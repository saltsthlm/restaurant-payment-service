package org.example.restaurantpaymentservice.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;


@Builder
public record KitchenEvent(
        UUID eventId,
        UUID ticketId,
        UUID orderId,
        TicketStatus status,
        Instant occurredAt) {




    public boolean validated() {
        if ((status.orderStatus().ordinal() != status.foodStatus().ordinal()) && !status.isFinished()) {
            return false;
        }
        if (occurredAt.isBefore(Instant.now())) return false;

        return true;
    }


}


