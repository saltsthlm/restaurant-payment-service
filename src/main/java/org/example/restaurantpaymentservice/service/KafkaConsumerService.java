package org.example.restaurantpaymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.restaurantpaymentservice.dto.Event;
import org.example.restaurantpaymentservice.dto.KitchenEvent;
import org.example.restaurantpaymentservice.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public class KafkaConsumerService {
    private final ObjectMapper mapper;
    private final PaymentService paymentService;

    public KafkaConsumerService(ObjectMapper mapper, PaymentService paymentService) {
        this.paymentService = paymentService;
        this.mapper = new ObjectMapper();
    }


    boolean dbStatusFilter(Event eventMessage){
        //take message id and
        UUID eventId= eventMessage.getOrderId();
        // check aginst db if it matches anything and has FAILED or REFUNDED as status ignore them.
        BigDecimal payment = eventMessage.getPaymentAmount();
        paymentService.refund(eventId,payment);
        return true;
    }

    @KafkaListener(id = "Order", topics = {"order.canceled", "order.canceled.v1"})
    public void listen(ConsumerRecord<String, String> record) throws JsonProcessingException {
        ///read message
        System.out.println("Kafka kitchen message: "+record.value());
        ///switch and handle the correct case
        switch (record.topic()) {
            case "Order.canceled":
                OrderEvent orderEvent = mapper.readValue(record.value(), OrderEvent.class);
                handleOrderCanceled(orderEvent);
                break;
            case "Kitchen.rejected":
                KitchenEvent kitchenEvent = mapper.readValue(record.value(), KitchenEvent.class);
                handleKitchenRejected(kitchenEvent);
                break;
            default:
                System.out.println("Unknown topic: " + record.topic());
        }
    }


    private void handleOrderCanceled(OrderEvent event) {
        System.out.println("Processing Order.canceled: " + event);
        // business logic here
    }

    private void handleKitchenRejected(KitchenEvent event) {
        System.out.println("Processing Kitchen.rejected: " + event);
        // business logic here
    }
}
