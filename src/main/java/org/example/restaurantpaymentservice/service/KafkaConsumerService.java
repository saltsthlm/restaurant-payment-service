package org.example.restaurantpaymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.restaurantpaymentservice.dto.KitchenEvent;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumerService {
    ObjectMapper mapper;

    public KafkaConsumerService(ObjectMapper mapper) {
        this.mapper = new ObjectMapper();
    }

    @KafkaListener(id = "kitchen", topics = "kitchen.prepared")
    void listen(String inMessage) throws JsonProcessingException {
        ///read message
        System.out.println("Kafka kitchen message: "+inMessage);
        //convert message to some sort of dto
        KitchenEvent event = mapper.readValue(inMessage, KitchenEvent.class);
        //validate message data

        //if bad reject message
        //else refund payment

    }
}
