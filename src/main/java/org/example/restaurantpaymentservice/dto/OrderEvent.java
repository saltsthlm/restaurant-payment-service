package org.example.restaurantpaymentservice.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.restaurantpaymentservice.enums.OrderStatus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderEvent(
    @NotNull UUID orderId,
    @NotNull OrderStatus orderStatus,
    @NotNull Instant createdAt,
    @Min(0) double totalPrice,
    @NotNull @NotEmpty List<OrderEventItem> items
) implements Event {
    @Override
    public UUID getOrderId() {
        return null;
    }

    @Override
    public BigDecimal getPaymentAmount() {
        return BigDecimal.valueOf(totalPrice);
    }

    @Override
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
