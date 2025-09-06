package ru.yandex.practicum.service.calculator;

import ru.yandex.practicum.model.DeliveryModel;
import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.warehouse.model.dto.AddressDto;

import java.math.BigDecimal;

public interface DeliveryCostCalculator {
    BigDecimal calculate(OrderModelDto orderModelDto, DeliveryModel delivery, AddressDto addressDto);
}
