package org.example.restaurantpaymentservice;
import org.example.restaurantpaymentservice.dto.KitchenEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantPaymentServiceApplicationTests {

    KitchenEvent queuedEvent;
    KitchenEvent inProgressEvent;
    KitchenEvent readyEvent;
    KitchenEvent handedOverEvent;
    KitchenEvent canceledEvent;
    List<KitchenEvent> mixedEvents = new ArrayList<>();

    @BeforeAll
    void setup() {
        queuedEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .status(KitchenEvent.Status.QUEUED)
                .stage(KitchenEvent.Stage.PENDING)
                .occurredAt(Instant.now())
                .build();

        inProgressEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .status(KitchenEvent.Status.IN_PROGRESS)
                .stage(KitchenEvent.Stage.IN_PROGRESS)
                .occurredAt(Instant.now())
                .build();

        readyEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .status(KitchenEvent.Status.READY)
                .stage(KitchenEvent.Stage.READY)
                .occurredAt(Instant.now())
                .build();

        handedOverEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .status(KitchenEvent.Status.HANDED_OVER)
                .stage(KitchenEvent.Stage.READY)
                .occurredAt(Instant.now())
                .build();

        canceledEvent = KitchenEvent.builder()
                .eventId(UUID.randomUUID())
                .ticketId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .status(KitchenEvent.Status.CANCELED)
                .stage(KitchenEvent.Stage.ACCEPTED)
                .reason(KitchenEvent.Reason.OPERATOR) // optional, but nice for canceled
                .occurredAt(Instant.now())
                .build();

        mixedEvents.add(queuedEvent);
        mixedEvents.add(inProgressEvent);
        mixedEvents.add(readyEvent);
        mixedEvents.add(handedOverEvent);
        mixedEvents.add(canceledEvent);
    }


    //make a test for each event that should fail because of nonsensical properties that should not match IE cancelld and in progress at the same time. Should get cought by validator.
    @Test
    @Disabled("Not yet implemented")
    void incorrectEventIsInvalid(){
    }




}
