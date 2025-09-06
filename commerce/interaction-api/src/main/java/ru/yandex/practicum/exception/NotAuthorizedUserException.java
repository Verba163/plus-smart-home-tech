package ru.yandex.practicum.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotAuthorizedUserException extends RuntimeException {
     String userMessage;
     String httpStatus;

    public NotAuthorizedUserException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
        this.httpStatus = "401 UNAUTHORIZED";
    }

}