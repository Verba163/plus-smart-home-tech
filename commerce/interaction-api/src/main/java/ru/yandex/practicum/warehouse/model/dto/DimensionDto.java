package ru.yandex.practicum.warehouse.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DimensionDto {

    @NotNull
    @Min(value = 1)
    Double width;

    @NotNull
    @Min(value = 1)
    Double height;

    @NotNull
    @Min(value = 1)
    Double depth;
}
