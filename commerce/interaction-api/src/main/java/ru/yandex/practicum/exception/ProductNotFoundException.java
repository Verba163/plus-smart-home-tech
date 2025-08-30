package ru.yandex.practicum.exception;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {
    String userMessage;
    String httpStatus;

    public ProductNotFoundException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
        this.httpStatus = "404 NOT_FOUND";
    }

}
