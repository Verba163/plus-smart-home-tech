package ru.yandex.practicum.cart.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeProductQuantityRequest {

    @NotNull(message = "Product ID cannot be null")
    UUID productId;


    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be greater than 0")
    Integer newQuantity;
}