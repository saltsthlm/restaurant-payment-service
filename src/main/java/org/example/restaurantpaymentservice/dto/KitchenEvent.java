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


        if (status.orderStatus().equals(TicketStatus.OrderStatus.CANCELED)){

        }
        else if ((status.orderStatus().ordinal() != status.foodStatus().ordinal())) {
            throw new IllegalStateException(
                    "OrderStatus and FoodStatus mismatch for unfinished event: " + status
            );
        }

        if (occurredAt.isAfter(Instant.now())) {
            throw new IllegalStateException(
                    "KitchenEvent cannot occur in the future: " + occurredAt
            );
        }
    }

}


