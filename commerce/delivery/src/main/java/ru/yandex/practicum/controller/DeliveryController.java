package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.delivery.model.dto.DeliveryModelDto;
import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.service.DeliveryService;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.yandex.practicum.constants.DeliveryApiPaths.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE)
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DeliveryModelDto createDelivery(@RequestBody DeliveryModelDto deliveryDto) {
        return deliveryService.createDelivery(deliveryDto);
    }

    @PostMapping(SUCCESSFUL)
    @ResponseStatus(HttpStatus.OK)
    public void successfulDelivery(@RequestParam UUID orderId) {
        deliveryService.successfulDelivery(orderId);
    }

    @PostMapping(PICKED)
    @ResponseStatus(HttpStatus.OK)
    public void pickedItemToDelivery(@RequestParam UUID orderId) {
        deliveryService.pickedItemToDelivery(orderId);
    }

    @PostMapping(FAILED)
    @ResponseStatus(HttpStatus.OK)
    public void failedDelivery(@RequestParam UUID orderId) {
        deliveryService.failedDelivery(orderId);
    }

    @PostMapping(COST)
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal calculateDeliveryCost(@RequestBody OrderModelDto orderModelDto) {
        return deliveryService.calculateDeliveryCost(orderModelDto);
    }
}