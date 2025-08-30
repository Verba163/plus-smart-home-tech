package ru.yandex.practicum.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NoProductInWarehouseException extends RuntimeException {
    String userMessage;
    String httpStatus;

    public NoProductInWarehouseException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
        this.httpStatus = "400 BAD_REQUEST";
    }

}