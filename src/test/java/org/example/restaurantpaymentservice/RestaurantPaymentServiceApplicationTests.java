package org.example.restaurantpaymentservice;
import org.example.restaurantpaymentservice.dto.KitchenEvent;
import org.example.restaurantpaymentservice.dto.TicketStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantPaymentServiceApplicationTests {

    KitchenEvent queuedEvent;
    KitchenEvent inProgressEvent;
    KitchenEvent readyEvent;
    KitchenEvent handedOverEvent;
    KitchenEvent canceledQueuedEvent;
    KitchenEvent canceledInProgressEvent;
    KitchenEvent canceledReadyEvent;

    List<KitchenEvent> mixedEvents = new ArrayList<>();
    List<KitchenEvent> expectedEvents = new ArrayList<>();


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

        canceledReadyEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .status(new TicketStatus(TicketStatus.OrderStatus.CANCELED, TicketStatus.FoodStatus.READY, Optional.empty()))
                .occurredAt(Instant.now())
                .build();

        //maybe also add cancelledHanded over event discuss with kitchen team
        expectedEvents.add(queuedEvent);
        expectedEvents.add(inProgressEvent);
        expectedEvents.add(readyEvent);
        expectedEvents.add(handedOverEvent);
        expectedEvents.add(canceledQueuedEvent);
        expectedEvents.add(canceledReadyEvent);
        expectedEvents.add(canceledInProgressEvent);
        for (TicketStatus.OrderStatus orderStatus : TicketStatus.OrderStatus.values()) {
            for (TicketStatus.FoodStatus foodStatus : TicketStatus.FoodStatus.values()) {
                mixedEvents.add(KitchenEvent.builder()
                        .eventId(UUID.randomUUID())
                        .ticketId(UUID.randomUUID())
                        .orderId(UUID.randomUUID())
                        .status(new TicketStatus(orderStatus, foodStatus, Optional.empty()))
                        .occurredAt(Instant.now())
                        .build());

                    for (TicketStatus.Reason reason : TicketStatus.Reason.values()) {
                        mixedEvents.add(KitchenEvent.builder()
                                .eventId(UUID.randomUUID())
                                .ticketId(UUID.randomUUID())
                                .orderId(UUID.randomUUID())
                                .status(new TicketStatus(orderStatus, foodStatus, Optional.of(reason)))
                                .occurredAt(Instant.now())
                                .build());
                    }
            }
        }

    }


    //make a test for each event that should fail because of nonsensical properties that should not match IE cancelld and in progress at the same time. Should get cought by validator.

    @Test
    void expectedEventsAreValid() {
        for (KitchenEvent event : expectedEvents) {
            assertDoesNotThrow(event::validate,
                    "Expected event to be valid, but validate() threw: " + event);
        }
    }

    @Test
    void unexpectedEventsAreInvalid() {
        for (KitchenEvent randEvent : mixedEvents) {
            boolean isExpected = expectedEvents.stream()
                    .anyMatch(validEvent -> validEvent.status().equals(randEvent.status()));
            if (isExpected) continue;

            if(randEvent.status().isFinished()){IllegalStateException ex = assertThrows(IllegalStateException.class,
                    randEvent::validate,
                    "Expected event to be invalid and throw, but it did not: " + randEvent);
                System.out.println("Caught expected exception: " + ex.getMessage());}
        }
    }
}
