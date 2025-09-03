package org.example.restaurantpaymentservice.service;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumerService {

    @KafkaListener(id = "kitchen", topics = "kitchen.prepared")
    void listen(String inMessage){
        ///read message
        System.out.println("Kafka kitchen message: "+inMessage);
        //convert message to some sort of dto
        //validate message data
        //if bad reject message
        //else refund payment

    }
}
