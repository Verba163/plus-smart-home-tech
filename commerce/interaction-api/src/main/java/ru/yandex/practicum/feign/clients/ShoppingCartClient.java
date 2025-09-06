package ru.yandex.practicum.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.model.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.model.dto.ShoppingCartDto;
import ru.yandex.practicum.constants.clients.FeignClientsConstants;
import ru.yandex.practicum.feign.config.ShoppingCartFeignConfig;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.yandex.practicum.constants.clients.FeignClientsConstants.BASE_PATH;
import static ru.yandex.practicum.constants.clients.FeignClientsConstants.REMOVE_ITEMS;

@FeignClient(name = "shopping-cart", configuration = ShoppingCartFeignConfig.class)
public interface ShoppingCartClient {

    @GetMapping(BASE_PATH)
    ShoppingCartDto getShoppingCart(@RequestParam("username") String username);

    @PutMapping(BASE_PATH)
    ShoppingCartDto putItemInCart(
            @RequestParam("username") String username,
            @RequestBody Map<UUID, Integer> items);

    @DeleteMapping(BASE_PATH)
    void deleteCart(@RequestParam("username") String username);

    @PostMapping(REMOVE_ITEMS)
    ShoppingCartDto removeItemFromCart(
            @RequestParam("username") String username,
            @RequestBody List<UUID> items);

    @PostMapping(FeignClientsConstants.CHANGE_QUANTITY)
    ShoppingCartDto changeItemQuantity(
            @RequestParam("username") String username,
            @RequestBody ChangeProductQuantityRequest quantityRequest);
}