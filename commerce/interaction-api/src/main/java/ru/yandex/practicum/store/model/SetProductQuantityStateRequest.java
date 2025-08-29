package ru.yandex.practicum.store.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.store.model.enums.QuantityState;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SetProductQuantityStateRequest {

    UUID productId;
    QuantityState quantityState;
}