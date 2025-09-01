package org.example.restaurantpaymentservice.dto;

import lombok.Builder;
import lombok.Data;
import org.example.restaurantpaymentservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class PaymentResponse {
    private UUID id;
    private String orderId;
    private BigDecimal amount;
    private String providerPaymentId;
    private PaymentStatus status;
    private Instant createdAt;
}
