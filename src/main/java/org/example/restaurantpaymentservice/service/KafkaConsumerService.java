package org.example.restaurantpaymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.restaurantpaymentservice.dto.KitchenEvent;
import org.example.restaurantpaymentservice.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumerService {
    private final ObjectMapper mapper;
    private final PaymentService paymentService;

    public KafkaConsumerService(ObjectMapper mapper, PaymentService paymentService) {
        this.paymentService = paymentService;
        this.mapper = new ObjectMapper();
    }


    boolean dbStatusFilter(OrderEvent orderEvent){
        //take message id and check aginst db if it matches anything and has FAILED or REFUNDED as status ignore them.

    }

    @KafkaListener(id = "Order", topics = "Order.canceled")
    void listen(String inMessage) throws JsonProcessingException {
        ///read message
        System.out.println("Kafka kitchen message: "+inMessage);
        //convert message to some sort of dto
        try {
            OrderEvent event = mapper.readValue(inMessage, OrderEvent.class);
            event.validate();
            System.out.println( event+" event message validated");


        } catch (Exception e) {
            System.err.println("Bad message, could not parse: " + inMessage);
            //if bad reject message
            // send to invalid-messages topic/table
            //refund payment
        }


    }
}
