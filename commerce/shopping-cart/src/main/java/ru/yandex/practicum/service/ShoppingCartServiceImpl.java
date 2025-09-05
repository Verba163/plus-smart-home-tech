package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.cart.model.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.model.dto.ShoppingCartDto;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.feign.clients.WarehouseClient;
import ru.yandex.practicum.mapper.ShoppingCartMapper;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.storage.ShoppingCartRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final String NO_CART_FOUND_MSG = "Shopping cart not found for user: %s";

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final WarehouseClient warehouseClient;

    @Transactional(readOnly = true)
    @Override
    public ShoppingCartDto getShoppingCart(String userName) {

        ShoppingCart cart = findCartByUserName(userName);

        return shoppingCartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addItemsToCart(String userName, Map<UUID, Integer> items) {

        ShoppingCart shoppingCart = shoppingCartRepository.findWithLockingByUserName(userName)
                .orElseGet(() -> {
                    ShoppingCart newCart = ShoppingCart.builder()
                            .id(UUID.randomUUID())
                            .userName(userName)
                            .products(new HashMap<>())
                            .build();
                    return shoppingCartRepository.save(newCart);
                });

        items.forEach((productId, quantity) -> {
            shoppingCart.getProducts().merge(productId, quantity, Integer::sum);
        });

        try {
            warehouseClient.checkProductQuantity(shoppingCartMapper.toDto(shoppingCart));
        } catch (ProductInShoppingCartLowQuantityInWarehouse e) {
            log.info("Insufficient stock for cart {}", shoppingCart.getId());
            throw e;
        }

        shoppingCartRepository.save(shoppingCart);
        log.info("Added products to user {}'s cart: {}", userName, items.keySet());
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void deleteCart(String userName) {

        ShoppingCart cart = findCartByUserName(userName);

        shoppingCartRepository.delete(cart);
        log.info("Deleted shopping cart for user {}", userName);
    }

    @Override
    public ShoppingCartDto removeItemsFromCart(String userName, List<UUID> itemIds) {

        ShoppingCart cart = findCartByUserName(userName);

        for (UUID itemId : itemIds) {
            if (!cart.getProducts().containsKey(itemId)) {
                throw new IllegalArgumentException(
                        String.format("Product with ID %s not found in user's cart", itemId)
                );
            }
            cart.getProducts().remove(itemId);
            log.info("Removed product {} from {}'s cart", itemId, userName);
        }

        shoppingCartRepository.save(cart);
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public ShoppingCartDto changeItemQuantity(String userName, ChangeProductQuantityRequest quantityRequest) {

        ShoppingCart shoppingCart = findCartByUserName(userName);

        UUID productId = quantityRequest.getProductId();

        if (!shoppingCart.getProducts().containsKey(productId)) {
            throw new IllegalArgumentException(
                    String.format("Product with ID %s not found in user's cart", productId)
            );
        }

        shoppingCart.getProducts().put(productId, quantityRequest.getNewQuantity());

        shoppingCartRepository.save(shoppingCart);

        log.info("Updated quantity of product {} in {}'s cart to {}", productId, userName, quantityRequest.getNewQuantity());

        return shoppingCartMapper.toDto(shoppingCart);
    }

    private ShoppingCart findCartByUserName(String userName) {
        String errorMessage = String.format(NO_CART_FOUND_MSG, userName);
        return shoppingCartRepository.findCartByUserName(userName)
                .orElseThrow(() -> new NoOrderFoundException(errorMessage, errorMessage));
    }
}