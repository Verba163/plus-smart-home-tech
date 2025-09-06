package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.payment.model.enums.PaymentStatus;

import java.util.UUID;

@Data
@Entity
@Table(name = "payments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", nullable = false)
    UUID paymentId;

    @Column(name = "order_id", nullable = false)
    UUID orderId;

    @Column(name = "total_payment", nullable = false)
    Double totalPayment;

    @Column(name = "delivery_total", nullable = false)
    Double deliveryTotal;

    @Column(name = "fee_total", nullable = false)
    Double feeTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    PaymentStatus paymentStatus;
}