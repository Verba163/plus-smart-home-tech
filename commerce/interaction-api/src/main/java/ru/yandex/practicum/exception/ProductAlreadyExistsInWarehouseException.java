package ru.yandex.practicum.exception;

import lombok.Getter;

@Getter
public class ProductAlreadyExistsInWarehouseException extends RuntimeException {
    private final String userMessage;
    private final String httpStatus;

    public ProductAlreadyExistsInWarehouseException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
        this.httpStatus = "400 BAD_REQUEST";
    }

}
