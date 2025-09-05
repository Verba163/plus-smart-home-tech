package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.NoPaymentFoundException;
import ru.yandex.practicum.feign.clients.OrderClient;
import ru.yandex.practicum.feign.clients.ShoppingStoreClient;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.PaymentModel;
import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.payment.model.dto.PaymentModelDto;
import ru.yandex.practicum.payment.model.enums.PaymentStatus;
import ru.yandex.practicum.repository.PaymentRepository;
import ru.yandex.practicum.store.model.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final ShoppingStoreClient shoppingStoreClient;

    @Value("${payment.fee_rate}")
    private Double feeRate;

    @Override
    @Transactional(readOnly = true)
    public Double getTotalCost(OrderModelDto orderModelDto) {
        PaymentModel payment = paymentRepository.findPaymentByPaymentId(orderModelDto.getPaymentId())
                .orElseThrow(() -> new NoPaymentFoundException(
                        (String.format("Payment not found for id: %s", orderModelDto.getPaymentId())),
                        (String.format("Payment not found for id: %s", orderModelDto.getPaymentId()))));

        Double productCost = this.productCost(orderModelDto).doubleValue();
        Double fee = productCost * feeRate;
        Double deliveryPrice = orderModelDto.getDeliveryPrice();
        Double totalCost = productCost + fee + deliveryPrice;

        payment.setTotalPayment(totalCost);
        paymentRepository.save(payment);
        return totalCost;
    }

    @Override
    @Transactional
    public PaymentModelDto createPayment(OrderModelDto orderModelDto) {
        Double totalPayment = getTotalCost(orderModelDto);
        BigDecimal productCost = this.productCost(orderModelDto);
        Double feeTotal = productCost.doubleValue() * feeRate;

        PaymentModel payment = PaymentModel.builder()
                .orderId(orderModelDto.getOrderId())
                .totalPayment(totalPayment)
                .deliveryTotal(orderModelDto.getDeliveryPrice())
                .feeTotal(feeTotal)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    @Transactional
    public void paymentFailed(UUID paymentId) {
        PaymentModel paymentModel = paymentRepository.findPaymentByPaymentId(paymentId)
                .orElseThrow(() -> new NoPaymentFoundException(
                        (String.format("Payment not found for id: %s", paymentId)),
                        (String.format("Payment not found for id: %s", paymentId))));

        updatePaymentStatus(paymentModel, PaymentStatus.FAILED);
        orderClient.markOrderPaymentFailed(paymentModel.getOrderId());
    }

    @Override
    @Transactional
    public void paymentSuccess(UUID paymentId) {
        PaymentModel paymentModel = paymentRepository.findPaymentByPaymentId(paymentId)
                .orElseThrow(() -> new NoPaymentFoundException(
                        (String.format("Payment not found for id: %s", paymentId)),
                        (String.format("Payment not found for id: %s", paymentId))));

        updatePaymentStatus(paymentModel, PaymentStatus.SUCCESS);
        orderClient.markOrderPaymentSuccessful(paymentModel.getOrderId());
    }

    @Override
    public BigDecimal productCost(OrderModelDto orderModelDto) {
        Map<UUID, Long> products = orderModelDto.getProducts();

        Map<UUID, BigDecimal> productPrices = products.keySet().stream()
                .map(shoppingStoreClient::getProduct)
                .collect(Collectors.toMap(ProductDto::getProductId, ProductDto::getPrice));

        return products.entrySet().stream()
                .map(entry -> {
                    BigDecimal price = productPrices.get(entry.getKey());
                    return (price != null) ? price.multiply(BigDecimal.valueOf(entry.getValue())) : BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void updatePaymentStatus(PaymentModel paymentModel, PaymentStatus status) {
        paymentModel.setPaymentStatus(status);
        paymentRepository.save(paymentModel);
    }
}