package ru.yandex.practicum.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

import static ru.yandex.practicum.constants.OrderApiConstants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderModelDto> getClientsOrders(@RequestParam String username) {
        return orderService.getOrdersByUsername(username);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto createNewOrder(@RequestBody @Valid CreateNewOrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PostMapping(RETURN)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto returnOrder(@RequestBody ProductReturnRequest returnRequest) {
        return orderService.processProductReturn(returnRequest);
    }

    @PostMapping(PAYMENT)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto orderPayment(@RequestParam UUID orderId) {
        return orderService.markPaymentAsSuccessful(orderId);
    }

    @PostMapping(PAYMENT_FAILED)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto orderPaymentFailed(@RequestParam UUID orderId) {
        return orderService.markPaymentAsFailed(orderId);
    }

    @PostMapping(DELIVERY)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto calculateOrderDeliveryCost(@RequestParam UUID orderId) {
        return orderService.calculateDeliveryCost(orderId);
    }

    @PostMapping(DELIVERY_FAILED)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto orderDeliveryFailed(@RequestParam UUID orderId) {
        return orderService.markDeliveryAsFailed(orderId);
    }

    @PostMapping(DELIVERY_SUCCESS)
    @ResponseStatus(HttpStatus.OK)
    public void orderDeliverySuccess(@RequestParam UUID orderId) {
        orderService.markDeliveryAsSuccessful(orderId);
    }

    @PostMapping(COMPLETED)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto orderCompleted(@RequestParam UUID orderId) {
        return orderService.markOrderAsCompleted(orderId);
    }

    @PostMapping(CALCULATE_TOTAL)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto calculateTotalOrderCost(@RequestParam UUID orderId) {
        return orderService.calculateTotalCost(orderId);
    }

    @PostMapping(CALCULATE_DELIVERY)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto calculateDeliveryOrderCost(@RequestParam UUID orderId) {
        return orderService.calculateDeliveryCost(orderId);
    }

    @PostMapping(ASSEMBLY)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto orderAssembly(@RequestParam UUID orderId) {
        return orderService.startOrderAssembly(orderId);
    }

    @PostMapping(ASSEMBLY_FAILED)
    @ResponseStatus(HttpStatus.OK)
    public OrderModelDto orderAssemblyFailed(@RequestParam UUID orderId) {
        return orderService.failOrderAssembly(orderId);
    }
}