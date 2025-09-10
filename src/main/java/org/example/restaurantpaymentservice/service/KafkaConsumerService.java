package org.example.restaurantpaymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.restaurantpaymentservice.dto.Event;
import org.example.restaurantpaymentservice.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;@Slf4j
@Component
public class KafkaConsumerService {
    private final ObjectMapper mapper;
    private final PaymentService paymentService;

    public KafkaConsumerService(ObjectMapper mapper, PaymentService paymentService) {
        this.mapper = mapper;
        this.paymentService = paymentService;
    }


    boolean dbStatusFilter(Event eventMessage){
        //take message id and
        UUID eventId= eventMessage.getOrderId();
        // check aginst db if it matches anything and has FAILED or REFUNDED as status ignore them.
        BigDecimal payment = eventMessage.getPaymentAmount();
        paymentService.refund(eventId,payment);
        return true;
    }

    @KafkaListener(topics = "order.canceled.v1")
    public void listen(ConsumerRecord<String, String> record) throws JsonProcessingException {
        System.out.printf(
                "Topic: %s, Partition: %d, Offset: %d, Message: %s%n",
                record.topic(),
                record.partition(),
                record.offset(),
                record.value()
        );

        String json = mapper.readValue(record.value(), String.class);
        OrderEvent event = mapper.readValue(json, OrderEvent.class);
        handleOrderCanceled(event);
    }


    private void handleOrderCanceled(OrderEvent event) {
        System.out.println("Processing Order.canceled: " + event);
        // business logic here
    }

}
