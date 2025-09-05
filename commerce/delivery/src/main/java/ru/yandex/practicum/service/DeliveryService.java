package ru.yandex.practicum.service;

import ru.yandex.practicum.delivery.model.dto.DeliveryModelDto;
import ru.yandex.practicum.order.model.dto.OrderModelDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface DeliveryService {

    DeliveryModelDto createDelivery(DeliveryModelDto deliveryDto);

    void successfulDelivery(UUID orderId);

    void pickedItemToDelivery(UUID orderId);

    void failedDelivery(UUID orderId);

    BigDecimal calculateDeliveryCost(OrderModelDto orderModelDto);
}