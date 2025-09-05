package ru.yandex.practicum.order.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.order.model.enums.OrderState;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderModelDto {

    @NotNull
    UUID orderId;

    @NotNull
    UUID shoppingCartId;

    @NotEmpty
    Map<UUID, Long> products;

    @NotNull
    UUID paymentId;

    @NotNull
    UUID deliveryId;

    @NotNull
    OrderState state;

    @NotNull
    Double deliveryWeight;

    @NotNull
    Double deliveryVolume;

    boolean fragile;

    @NotNull
    Double totalPrice;

    @NotNull
    Double deliveryPrice;

    @NotNull
    Double productPrice;
}

