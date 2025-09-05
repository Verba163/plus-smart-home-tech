package ru.yandex.practicum.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.feign.config.PaymentFeignConfig;
import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.payment.model.dto.PaymentModelDto;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.yandex.practicum.constants.clients.FeignClientsConstants.*;

@FeignClient(name = "payment-service", configuration = PaymentFeignConfig.class)
public interface PaymentClient {

    @PostMapping(BASE_PATH_PAYMENT)
    PaymentModelDto processPayment(@RequestBody OrderModelDto orderModelDto);

    @PostMapping(TOTAL_COST_PATH)
    BigDecimal calculateTotalCost(@RequestBody OrderModelDto orderModelDto);

    @PostMapping(REFUND_PATH)
    void processRefund(@RequestParam UUID paymentId);

    @PostMapping(PRODUCT_COST_PATH)
    BigDecimal calculateProductCost(@RequestBody OrderModelDto orderModelDto);

    @PostMapping(FAILED_PATH_PAYMENT)
    void markPaymentFailed(@RequestParam UUID paymentId);

    @PostMapping(SUCCESS_PATH_PAYMENT)
    void markPaymentSuccessful(@RequestParam UUID paymentId);
}