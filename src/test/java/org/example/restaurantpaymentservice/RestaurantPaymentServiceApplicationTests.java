package org.example.restaurantpaymentservice;
import org.example.restaurantpaymentservice.dto.KitchenEvent;
import org.example.restaurantpaymentservice.dto.TicketStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantPaymentServiceApplicationTests {

    KitchenEvent queuedEvent;
    KitchenEvent inProgressEvent;
    KitchenEvent readyEvent;
    KitchenEvent handedOverEvent;
    KitchenEvent canceledQueuedEvent;
    KitchenEvent canceledInProgressEvent;
    KitchenEvent canceledReadyEvent;
    KitchenEvent canceledHandedOverEvent;


    List<KitchenEvent> mixedEvents = new ArrayList<>();

    @BeforeAll
    void setup() {
        queuedEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .status(new TicketStatus(TicketStatus.OrderStatus.QUEUED, TicketStatus.FoodStatus.QUEUED, Optional.empty()))
                .occurredAt(Instant.now())
                .build();

        inProgressEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .status(new TicketStatus(TicketStatus.OrderStatus.IN_PROGRESS, TicketStatus.FoodStatus.IN_PROGRESS, Optional.empty()))
                .occurredAt(Instant.now())
                .build();


        readyEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .status(new TicketStatus(TicketStatus.OrderStatus.READY, TicketStatus.FoodStatus.READY, Optional.empty()))
                .occurredAt(Instant.now())
                .build();

        handedOverEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .status(new TicketStatus(TicketStatus.OrderStatus.HANDED_OVER, TicketStatus.FoodStatus.HANDED_OVER, Optional.empty()))
                .occurredAt(Instant.now())
                .build();

        canceledQueuedEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .status(new TicketStatus(TicketStatus.OrderStatus.CANCELED, TicketStatus.FoodStatus.QUEUED, Optional.empty()))
                .occurredAt(Instant.now())
                .build();

        canceledInProgressEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .status(new TicketStatus(TicketStatus.OrderStatus.CANCELED, TicketStatus.FoodStatus.IN_PROGRESS, Optional.empty()))
                .occurredAt(Instant.now())
                .build();

        mixedEvents.add(queuedEvent);
        mixedEvents.add(inProgressEvent);
        mixedEvents.add(readyEvent);
        mixedEvents.add(handedOverEvent);
    }


    //make a test for each event that should fail because of nonsensical properties that should not match IE cancelld and in progress at the same time. Should get cought by validator.
    @Test
    @Disabled("Not yet implemented")
    void incorrectEventIsInvalid(){
    }




}
