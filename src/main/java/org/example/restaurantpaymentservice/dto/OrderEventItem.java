package org.example.restaurantpaymentservice.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record OrderEventItem(
    @NotNull UUID id,
    @NotNull @Min(0) Integer itemId,
    @Min(1) int quantity,
    @Min(0) double price
) {


}
