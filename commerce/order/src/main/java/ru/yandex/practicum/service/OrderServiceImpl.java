package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.cart.model.dto.ShoppingCartDto;
import ru.yandex.practicum.delivery.model.dto.DeliveryModelDto;
import ru.yandex.practicum.delivery.model.enums.DeliveryState;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.feign.clients.DeliveryClient;
import ru.yandex.practicum.feign.clients.PaymentClient;
import ru.yandex.practicum.feign.clients.ShoppingCartClient;
import ru.yandex.practicum.feign.clients.WarehouseClient;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.OrderModel;
import ru.yandex.practicum.order.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.order.model.enums.OrderState;
import ru.yandex.practicum.payment.model.dto.PaymentModelDto;
import ru.yandex.practicum.repository.OrderRepository;
import ru.yandex.practicum.warehouse.model.dto.BookedProductsDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ShoppingCartClient shoppingCartClient;
    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClient paymentClient;

    @Override
    public List<OrderModelDto> getOrdersByUsername(String username) {

        ShoppingCartDto shoppingCartDto = shoppingCartClient.getShoppingCart(username);

        return orderRepository.findByShoppingCartId(shoppingCartDto.getId()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderModelDto createOrder(CreateNewOrderRequest request) {

        OrderModel orderModel = OrderModel.builder()
                .shoppingCartId(request.getShoppingCart().getId())
                .products(request.getShoppingCart().getProducts())
                .state(OrderState.NEW)
                .build();
        orderRepository.save(orderModel);

        AssemblyProductsForOrderRequest assemblyRequest = AssemblyProductsForOrderRequest.builder()
                .orderId(orderModel.getOrderId())
                .products(request.getShoppingCart().getProducts())
                .build();
        BookedProductsDto bookedProductsDto = warehouseClient.assembleProductsForDelivery(assemblyRequest);

        DeliveryModelDto deliveryModelDto = DeliveryModelDto.builder()
                .orderId(orderModel.getOrderId())
                .fromAddress(warehouseClient.getWarehouseAddress())
                .toAddress(request.getDeliveryAddress())
                .deliveryState(DeliveryState.CREATED)
                .build();

        deliveryModelDto = deliveryClient.createDelivery(deliveryModelDto);

        PaymentModelDto paymentDto = paymentClient.processPayment(orderMapper.toDto(orderModel));

        orderModel.setPaymentId(paymentDto.getPaymentId());
        orderModel.setDeliveryId(deliveryModelDto.getDeliveryId());
        orderModel.setDeliveryWeight(bookedProductsDto.getDeliveryWeight());
        orderModel.setDeliveryVolume(bookedProductsDto.getDeliveryVolume());
        orderModel.setFragile(bookedProductsDto.getFragile());
        orderModel.setTotalPrice(paymentClient.calculateTotalCost(orderMapper.toDto(orderModel)).doubleValue());
        orderModel.setDeliveryPrice(deliveryClient.calculateDeliveryCost(orderMapper.toDto(orderModel)).doubleValue());
        orderModel.setProductPrice(paymentClient.calculateProductCost(orderMapper.toDto(orderModel)).doubleValue());

        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto startOrderAssembly(UUID orderId) {
        OrderModel orderModel = orderRepository.findById(orderId).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", orderId),
                        String.format("Order not found for id: %s", orderId)
                ));
        orderModel.setState(OrderState.ASSEMBLED);
        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto failOrderAssembly(UUID orderId) {
        OrderModel orderModel = orderRepository.findById(orderId).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", orderId),
                        String.format("Order not found for id: %s", orderId)
                ));
        orderModel.setState(OrderState.ASSEMBLY_FAILED);
        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto calculateDeliveryCost(UUID orderId) {
        OrderModel orderModel = orderRepository.findById(orderId).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", orderId),
                        String.format("Order not found for id: %s", orderId)
                ));

        BigDecimal totalCost = deliveryClient.calculateDeliveryCost(orderMapper.toDto(orderModel));
        orderModel.setDeliveryPrice(totalCost.doubleValue());
        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto calculateTotalCost(UUID orderId) {
        OrderModel orderModel = orderRepository.findById(orderId).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", orderId),
                        String.format("Order not found for id: %s", orderId)
                ));
        BigDecimal totalCost = paymentClient.calculateTotalCost(orderMapper.toDto(orderModel));
        orderModel.setTotalPrice(totalCost.doubleValue());
        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto markOrderAsCompleted(UUID orderId) {
        OrderModel orderModel = orderRepository.findById(orderId).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", orderId),
                        String.format("Order not found for id: %s", orderId)
                ));
        orderModel.setState(OrderState.COMPLETED);
        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto markDeliveryAsSuccessful(UUID orderId) {
        OrderModel orderModel = orderRepository.findById(orderId).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", orderId),
                        String.format("Order not found for id: %s", orderId)
                ));
        orderModel.setState(OrderState.DELIVERED);
        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto markDeliveryAsFailed(UUID orderId) {
        OrderModel orderModel = orderRepository.findById(orderId).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", orderId),
                        String.format("Order not found for id: %s", orderId)
                ));
        orderModel.setState(OrderState.DELIVERY_FAILED);
        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto markPaymentAsSuccessful(UUID orderId) {
        OrderModel orderModel = orderRepository.findById(orderId).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", orderId),
                        String.format("Order not found for id: %s", orderId)
                ));
        orderModel.setState(OrderState.PAID);
        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto markPaymentAsFailed(UUID orderId) {
        OrderModel orderModel = orderRepository.findById(orderId).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", orderId),
                        String.format("Order not found for id: %s", orderId)
                ));
        orderModel.setState(OrderState.PAYMENT_FAILED);
        return orderMapper.toDto(orderRepository.save(orderModel));
    }

    @Override
    public OrderModelDto processProductReturn(ProductReturnRequest request) {
        OrderModel orderModel = orderRepository.findById(request.getOrderId()).
                orElseThrow(() -> new NoOrderFoundException(
                        String.format("Order not found for id: %s", request.getOrderId()),
                        String.format("Order not found for id: %s", request.getOrderId())
                ));
        warehouseClient. acceptReturn(request.getProducts());

        orderModel.setState(OrderState.PRODUCT_RETURNED);
        return orderMapper.toDto(orderRepository.save(orderModel));
    }
}
