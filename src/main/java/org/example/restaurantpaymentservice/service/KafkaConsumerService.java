package org.example.restaurantpaymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.restaurantpaymentservice.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class KafkaConsumerService {
    private final ObjectMapper mapper;
    private final PaymentService paymentService;

    public KafkaConsumerService(ObjectMapper mapper, PaymentService paymentService) {
        this.mapper = mapper;
        this.paymentService = paymentService;

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
        BigDecimal payment = BigDecimal.valueOf(event.totalPrice());
        paymentService.refund(event.eventId(),payment);

    }

}
