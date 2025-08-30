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
    @Column(name = "id", updatable = false, nullable = false)
    UUID productId;

    @Column(name = "fragile", nullable = false)
    Boolean fragile;

    @Column(name = "width", nullable = false)
    double width;

    @Column(name = "height", nullable = false)
    double height;

    @Column(name = "depth", nullable = false)
    double depth;

    @Column(name = "weight", nullable = false)
    Double weight;

    @Column(name = "quantity", nullable = false)
    long quantity;

    @Column(name = "reserved_quantity", nullable = false)
    long reservedQuantity;
}