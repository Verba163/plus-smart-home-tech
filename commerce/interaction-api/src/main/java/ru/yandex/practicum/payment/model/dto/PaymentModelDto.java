package ru.yandex.practicum.payment.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentModelDto {

    @NotNull
    UUID paymentId;

    @NotNull
    Double totalPayment;

    @NotNull
    Double deliveryTotal;

    @NotNull
    Double feeTotal;
}

