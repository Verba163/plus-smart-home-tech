package ru.yandex.practicum.service;

import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.order.model.dto.OrderModelDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderModelDto> getOrdersByUsername(String username);

    OrderModelDto createOrder(CreateNewOrderRequest request);

    OrderModelDto startOrderAssembly(UUID orderId);

    OrderModelDto failOrderAssembly(UUID orderId);

    OrderModelDto calculateDeliveryCost(UUID orderId);

    OrderModelDto calculateTotalCost(UUID orderId);

    OrderModelDto markOrderAsCompleted(UUID orderId);

    OrderModelDto markDeliveryAsSuccessful(UUID orderId);

    OrderModelDto markDeliveryAsFailed(UUID orderId);

    OrderModelDto markPaymentAsSuccessful(UUID orderId);

    OrderModelDto markPaymentAsFailed(UUID orderId);

    OrderModelDto processProductReturn(ProductReturnRequest request);
}
