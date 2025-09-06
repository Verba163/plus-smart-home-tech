package ru.yandex.practicum.feign.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.delivery.model.dto.DeliveryModelDto;
import ru.yandex.practicum.feign.config.DeliveryFeignConfig;
import ru.yandex.practicum.order.model.dto.OrderModelDto;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.yandex.practicum.constants.clients.FeignClientsConstants.*;

@FeignClient(name = "delivery-service", configuration = DeliveryFeignConfig.class)
public interface DeliveryClient {

    @PutMapping(BASE_PATH_DELIVERY)
    DeliveryModelDto createDelivery(@RequestBody DeliveryModelDto deliveryModelDto);

    @PostMapping(SUCCESSFUL_PATH)
    void markDeliverySuccessful(@RequestParam UUID orderId);

    @PostMapping(PICKED_PATH)
    void markItemPickedForDelivery(@RequestParam UUID orderId);

    @PostMapping(FAILED_PATH)
    void markDeliveryFailed(@RequestParam UUID orderId);

    @PostMapping(COST_PATH)
    BigDecimal calculateDeliveryCost(@RequestBody OrderModelDto orderModelDto);
}
