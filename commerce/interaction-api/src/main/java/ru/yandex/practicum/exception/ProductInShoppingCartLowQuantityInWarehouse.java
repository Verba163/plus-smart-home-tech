package ru.yandex.practicum.exception;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class ProductInShoppingCartLowQuantityInWarehouse extends RuntimeException {
    private final String userMessage;
    private final String httpStatus = "400 BAD_REQUEST";
    private final List<UUID> missingProducts;

    public ProductInShoppingCartLowQuantityInWarehouse(String message, List<UUID> missingProducts) {
        super(message);
        this.userMessage = message;
        this.missingProducts = missingProducts;
    }

}
