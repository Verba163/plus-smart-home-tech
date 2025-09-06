package ru.yandex.practicum.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponseForWarehouseCheck extends ErrorResponse {
    List<UUID> missingProducts;
}