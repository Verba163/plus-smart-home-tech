package ru.yandex.practicum.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NoPaymentFoundException extends RuntimeException {

    String userMessage;
    String httpStatus;

    public NoPaymentFoundException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
        this.httpStatus = "404 NOT_FOUND";
    }

}