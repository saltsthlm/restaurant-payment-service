package org.example.restaurantpaymentservice.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentCreateRequest {
    private String orderId;

    private BigDecimal amount;

    private String providerPaymentId;
}
