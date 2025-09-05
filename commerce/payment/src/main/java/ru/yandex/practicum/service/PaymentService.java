package ru.yandex.practicum.service;

import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.payment.model.dto.PaymentModelDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {

    Double getTotalCost(OrderModelDto orderModelDto);

    PaymentModelDto createPayment(OrderModelDto orderModelDto);

    void paymentFailed(UUID paymentId);

    void paymentSuccess(UUID paymentId);

    BigDecimal productCost(OrderModelDto orderModelDto);
}

