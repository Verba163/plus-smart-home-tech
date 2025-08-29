package ru.yandex.practicum.exception;

import lombok.Getter;

@Getter
public class NoProductInWarehouseException extends RuntimeException {
    private final String userMessage;
    private final String httpStatus;

    public NoProductInWarehouseException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
        this.httpStatus = "400 BAD_REQUEST";
    }

}