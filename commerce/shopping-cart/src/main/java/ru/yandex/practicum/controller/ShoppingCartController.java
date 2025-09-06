package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.model.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.model.dto.ShoppingCartDto;
import ru.yandex.practicum.service.ShoppingCartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.yandex.practicum.costants.ShoppingCartApi.*;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService cartService;

    @GetMapping(API_PREFIX)
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto getShoppingCart(@RequestParam @NotBlank String username) {
        log.info("Request to retrieve shopping cart for user: {}", username);
        return cartService.getShoppingCart(username);
    }

    @PutMapping(API_PREFIX)
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto putItemInCart(@RequestParam @NotBlank String username,
                                         @RequestBody Map<UUID, Integer> items) {
        log.info("Adding items to user {}'s cart: {}", username, items);
        return cartService.addItemsToCart(username, items);
    }

    @DeleteMapping(API_PREFIX)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCart(@RequestParam @NotBlank String username) {
        log.info("Deleting shopping cart for user: {}", username);
        cartService.deleteCart(username);
    }

    @PostMapping(API_PREFIX + API_PREFIX_REMOVE)
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto removeItemFromCart(@RequestParam @NotBlank String username,
                                              @RequestBody List<UUID> items) {
        log.info("Removing items {} from user {}'s cart", items, username);
        return cartService.removeItemsFromCart(username, items);
    }

    @PostMapping(API_PREFIX + API_PREFIX_CHANGE_QUANTITY)
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto changeItemQuantity(@RequestParam @NotBlank String username,
                                              @RequestBody @Valid ChangeProductQuantityRequest quantityRequest) {
        log.info("Changing quantity of product {} to {} in user {}'s cart",
                quantityRequest.getProductId(), quantityRequest.getNewQuantity(), username);
        return cartService.changeItemQuantity(username, quantityRequest);
    }
}