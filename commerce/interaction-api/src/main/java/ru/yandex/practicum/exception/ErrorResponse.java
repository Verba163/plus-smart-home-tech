package ru.yandex.practicum.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
     String httpStatus;
     String userMessage;
     String message;
     String ex;
     StackTraceElementDto[] stackTrace;
     Object cause;
     Object[] suppressed;
     String localizedMessage;
}

