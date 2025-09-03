package org.example.restaurantpaymentservice.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;


@Builder
public record KitchenEvent(
        UUID eventId,
        UUID ticketId,
        UUID orderId,
        Status status,   // CANCELED
        Stage stage,     // PENDING | ACCEPTED | IN_PROGRESS | READY
        Reason reason,   // CLOSED | CAPACITY | ORDER_CANCELED | PAYMENT_FAILED | OPERATOR
        Instant occurredAt)    // 2025-09-01T12:34:56Z
{
    public enum Status {
        CANCELED
    }

    public enum Stage {
        PENDING, ACCEPTED, IN_PROGRESS, READY
    }

    public enum Reason {
        CLOSED, CAPACITY, ORDER_CANCELED, PAYMENT_FAILED, OPERATOR
    }

    public boolean validated(){
        //occured is only in the past but not too faar
        if (occurredAt.isBefore(Instant.now())) return false;

        return true;
    }


}


