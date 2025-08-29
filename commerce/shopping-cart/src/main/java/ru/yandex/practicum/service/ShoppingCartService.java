package ru.yandex.practicum.service;

import ru.yandex.practicum.cart.model.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.model.dto.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart(String userName);

    ShoppingCartDto addItemsToCart(String userName, Map<UUID, Integer> items);

    void deleteCart(String userName);

    ShoppingCartDto removeItemsFromCart(String userName, List<UUID> items);

    ShoppingCartDto changeItemQuantity(String userName, ChangeProductQuantityRequest quantityRequest);
}
