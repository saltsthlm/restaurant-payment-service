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
        Status status,
        Stage stage,
        @Nullable Reason reason,
        Instant occurredAt)
{
    public enum Status {
        QUEUED,
        IN_PROGRESS,
        READY,
        HANDED_OVER,
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


