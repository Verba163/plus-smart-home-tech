package ru.yandex.practicum.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.feign.config.OrderFeignConfig;
import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.order.model.dto.OrderModelDto;

import java.util.UUID;

import static ru.yandex.practicum.constants.clients.FeignClientsConstants.*;

@FeignClient(name = "order-service", configuration = OrderFeignConfig.class)
public interface OrderClient {

    @GetMapping(BASE_PATH_DELIVERY)
    OrderModelDto getOrdersByUsername(@RequestParam String username);

    @PutMapping(BASE_PATH_DELIVERY)
    OrderModelDto createOrder(@RequestBody CreateNewOrderRequest orderRequest);

    @PostMapping(RETURN_PATH)
    OrderModelDto processOrderReturn(@RequestBody ProductReturnRequest returnRequest);

    @PostMapping(PAYMENT_PATH)
    OrderModelDto processOrderPayment(@RequestParam UUID orderId);

    @PostMapping(PAYMENT_SUCCESS_PATH)
    void markOrderPaymentSuccessful(@RequestParam UUID orderId);

    @PostMapping(PAYMENT_FAILED_PATH)
    OrderModelDto markOrderPaymentFailed(@RequestParam UUID orderId);

    @PostMapping(DELIVERY_PATH)
    OrderModelDto processOrderDelivery(@RequestParam UUID orderId);

    @PostMapping(DELIVERY_SUCCESS_PATH)
    void markOrderDeliverySuccessful(@RequestParam UUID orderId);

    @PostMapping(DELIVERY_FAILED_PATH)
    OrderModelDto markOrderDeliveryFailed(@RequestParam UUID orderId);

    @PostMapping(COMPLETED_PATH)
    OrderModelDto markOrderCompleted(@RequestParam UUID orderId);

    @PostMapping(CALCULATE_TOTAL_PATH)
    OrderModelDto calculateTotalOrderCost(@RequestParam UUID orderId);

    @PostMapping(CALCULATE_DELIVERY_PATH)
    OrderModelDto calculateDeliveryOrderCost(@RequestParam UUID orderId);

    @PostMapping(ASSEMBLY_PATH)
    OrderModelDto processOrderAssembly(@RequestParam UUID orderId);

    @PostMapping(ASSEMBLY_SUCCESS_PATH)
    void markOrderAssemblySuccessful(@RequestParam UUID orderId);

    @PostMapping(ASSEMBLY_FAILED_PATH)
    OrderModelDto markOrderAssemblyFailed(@RequestParam UUID orderId);
}