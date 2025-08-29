package ru.yandex.practicum.exception;

import lombok.Getter;

@Getter
public class NotAuthorizedUserException extends RuntimeException {
    private final String userMessage;
    private final String httpStatus;

    public NotAuthorizedUserException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
        this.httpStatus = "401 UNAUTHORIZED";
    }

}