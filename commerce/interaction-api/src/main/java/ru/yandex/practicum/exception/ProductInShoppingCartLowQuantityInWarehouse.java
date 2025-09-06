package ru.yandex.practicum.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductInShoppingCartLowQuantityInWarehouse extends RuntimeException {
    String userMessage;
    String httpStatus = "400 BAD_REQUEST";
    List<UUID> missingProducts;

    public ProductInShoppingCartLowQuantityInWarehouse(String message, List<UUID> missingProducts) {
        super(message);
        this.userMessage = message;
        this.missingProducts = missingProducts;
    }

}
