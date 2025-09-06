package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.payment.model.dto.PaymentModelDto;
import ru.yandex.practicum.service.PaymentService;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.yandex.practicum.constants.PaymentApiPaths.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE)
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public PaymentModelDto payment(@RequestBody OrderModelDto orderDto) {
        return paymentService.createPayment(orderDto);
    }

    @PostMapping(TOTAL_COST)
    public Double calculateTotalCost(@RequestBody OrderModelDto orderModelDto) {
        return paymentService.getTotalCost(orderModelDto);
    }

    @PostMapping(PRODUCT_COST)
    public BigDecimal productCost(@RequestBody OrderModelDto orderModelDto) {
        return paymentService.productCost(orderModelDto);
    }

    @PostMapping(FAILED)
    public void paymentFailed(@RequestParam UUID paymentId) {
        paymentService.paymentFailed(paymentId);
    }

    @PostMapping(SUCCESS)
    public void paymentSuccess(@RequestParam UUID paymentId) {
        paymentService.paymentSuccess(paymentId);
    }
}
