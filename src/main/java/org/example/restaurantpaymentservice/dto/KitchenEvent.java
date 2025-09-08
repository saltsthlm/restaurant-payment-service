package org.example.restaurantpaymentservice.dto;
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

    public void validate() {
        if (status.isFinished() &&(status.orderStatus().ordinal() != status.foodStatus().ordinal())) {
            throw new IllegalStateException(
                    "OrderStatus and FoodStatus mismatch for unfinished event: " + status
            );
        }

        if (status.orderStatus() != TicketStatus.OrderStatus.CANCELED && status.cancelReason().isPresent()) {
            throw new IllegalStateException(
                    "Non-canceled event cannot have a cancelReason: " + status
            );
        }

        if (occurredAt.isAfter(Instant.now())) {
            throw new IllegalStateException(
                    "KitchenEvent cannot occur in the future: " + occurredAt
            );
        }
    }

}


