package org.example.restaurantpaymentservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.restaurantpaymentservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Payment {

    @Id
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "provider_payment_id", nullable = false)
    private String providerPaymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "failure_reason")
    private String failureReason;
}

