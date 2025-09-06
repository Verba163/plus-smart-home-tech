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
@Table(name = "warehouse_item_reserved")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @NotNull
    @Column(name = "shopping_cart_id", nullable = false)
    UUID shoppingCartId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    UUID productId;

    @NotNull
    @Column(name = "reserved_quantity", nullable = false)
    Long reservedQuantity;
}