package ru.yandex.practicum.exception;

import lombok.Getter;

@Getter
public class ShoppingCartNotFoundException extends RuntimeException {
    private final String userMessage;
    private final String httpStatus;

    public ShoppingCartNotFoundException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
        this.httpStatus = "404 NOT_FOUND";
    }

}
