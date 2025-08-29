package ru.yandex.practicum.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ErrorResponseForWarehouseCheck extends ErrorResponse {
    private List<UUID> missingProducts;
}