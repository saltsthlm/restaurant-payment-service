package org.example.restaurantpaymentservice;

import jdk.jshell.spi.ExecutionControl;
import org.example.restaurantpaymentservice.dto.KitchenEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.jayway.jsonpath.internal.Utils.isTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantPaymentServiceApplicationTests {

    KitchenEvent queuedEvent;
    KitchenEvent inProgressEvent;
    KitchenEvent ReadyEvent;
    KitchenEvent HandedOverEvent;
    KitchenEvent CanceledEvent;
    List<KitchenEvent> mixedEvents = new ArrayList<>();


    @BeforeAll
    void setup() {
        mixedEvents.add(queuedEvent = new KitchenEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                KitchenEvent.Status.QUEUED,
                KitchenEvent.Stage.PENDING,
                KitchenEvent.Reason.OPERATOR,
                Instant.now().minusSeconds(100)
        ));

        mixedEvents.add(inProgressEvent);
        mixedEvents.add(ReadyEvent);
        mixedEvents.add(HandedOverEvent);
        mixedEvents.add(CanceledEvent);
    }

    @Test
    @Disabled("Not yet implemented")
    void incorrectEventIsInvalid(){
    }

    @Test
    @Disabled("Not yet implemented")
    void correctEventIsCorrect(){
    }

    @Test
    @Disabled("Not yet implemented")
    void queuedEventNotYetHandled(){
    }

    @Test
    @Disabled("Not yet implemented")
    void inProgressEventinProgres(){
    }

    @Test
    @Disabled("Not yet implemented")
    void ReadyEventReady(){
    }

    @Test
    @Disabled("Not yet implemented")
    void HandedOverEventHandedOver(){
    }

    @Test
    @Disabled("Not yet implemented")
    void CanceledEventCanceled(){
    }

    @Test
    @Disabled("Not yet implemented")
    void mixedEvents(){
    }


}
