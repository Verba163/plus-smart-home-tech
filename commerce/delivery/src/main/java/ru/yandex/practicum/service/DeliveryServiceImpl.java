package ru.yandex.practicum.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.delivery.model.ShippedToDeliveryRequest;
import ru.yandex.practicum.delivery.model.dto.DeliveryModelDto;
import ru.yandex.practicum.delivery.model.enums.DeliveryState;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.feign.clients.OrderClient;
import ru.yandex.practicum.feign.clients.WarehouseClient;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.DeliveryModel;
import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.repository.DeliveryRepository;
import ru.yandex.practicum.service.calculator.DeliveryCostCalculator;
import ru.yandex.practicum.warehouse.model.dto.AddressDto;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final OrderClient orderClient;
    private final WarehouseClient warehouseClient;
    private final DeliveryCostCalculator deliveryCostCalculator;

    @Override
    @Transactional
    public DeliveryModelDto createDelivery(DeliveryModelDto deliveryModelDto) {

        DeliveryModel deliveryModel = deliveryMapper.toEntity(deliveryModelDto);
        deliveryModel.setDeliveryState(DeliveryState.CREATED);

        return deliveryMapper.toDto(deliveryRepository.save(deliveryModel));
    }

    @Override
    @Transactional
    public void successfulDelivery(UUID orderId) {
        log.info("Attempting to complete delivery for order {}", orderId);

        DeliveryModel delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("No delivery found for orderId: %s", orderId),
                        String.format("No delivery found for orderId: %s", orderId)));

        delivery.setDeliveryState(DeliveryState.DELIVERED);
        orderClient.markOrderDeliverySuccessful(orderId);
        deliveryRepository.save(delivery);

        log.info("Delivery for order {} successfully completed", orderId);
    }

    @Override
    @Transactional
    public void pickedItemToDelivery(UUID orderId) {
        DeliveryModel delivery = deliveryRepository.findByOrderId(orderId).
                orElseThrow(() -> new NoOrderFoundException(String.format("No delivery found for orderId: %s", orderId),
                        String.format("No delivery found for orderId: %s", orderId)));

        orderClient.processOrderAssembly(delivery.getOrderId());

        ShippedToDeliveryRequest request = ShippedToDeliveryRequest.builder()
                .orderId(delivery.getOrderId())
                .deliveryId(delivery.getDeliveryId())
                .build();

        warehouseClient.processShipmentToDelivery(request);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void failedDelivery(UUID orderId) {
        log.info("Attempting to set delivery as failed for order {}", orderId);

        DeliveryModel delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("No delivery found for orderId: %s", orderId),
                        String.format("No delivery found for orderId: %s", orderId)));

        delivery.setDeliveryState(DeliveryState.FAILED);
        orderClient.markOrderDeliveryFailed(orderId);
        deliveryRepository.save(delivery);

        log.info("Delivery for order {} failed and not completed", orderId);
    }

    @Override
    public BigDecimal calculateDeliveryCost(OrderModelDto orderModelDto) {

        DeliveryModel delivery = deliveryRepository.findByDeliveryId(orderModelDto.getDeliveryId())
                .orElseThrow(() -> new NoDeliveryFoundException("No delivery found for deliveryId: " + orderModelDto.getDeliveryId(),
                        "No delivery found for deliveryId: " + orderModelDto.getDeliveryId()));

        AddressDto warehouseAddress = warehouseClient.getWarehouseAddress();

        return deliveryCostCalculator.calculate(orderModelDto, delivery, warehouseAddress);
    }
}