package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouse_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @NotNull
    @Column(name = "fragile", nullable = false)
    Boolean fragile;

    @NotNull
    @Column(name = "width", nullable = false)
    Double width;

    @NotNull
    @Column(name = "height", nullable = false)
    Double height;

    @NotNull
    @Column(name = "depth", nullable = false)
    Double depth;

    @NotNull
    @Column(name = "weight", nullable = false)
    Double weight;

    @NotNull
    @Column(name = "quantity", nullable = false)
    Long quantity;

    @NotNull
    @Column(name = "reserved_quantity", nullable = false)
    Long reservedQuantity;
}