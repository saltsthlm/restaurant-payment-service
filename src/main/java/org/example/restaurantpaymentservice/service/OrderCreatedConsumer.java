package org.example.restaurantpaymentservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restaurantpaymentservice.evenDto.OrderMessageDto;
import org.example.restaurantpaymentservice.idempotent.ConsumedEvent;
import org.example.restaurantpaymentservice.idempotent.ConsumedEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@AllArgsConstructor
public class OrderCreatedConsumer {

    private final ObjectMapper mapper;
    private final PaymentService paymentService;
    private final ConsumedEventRepository consumedEventRepository;

    @Transactional
    @KafkaListener(topics = "order.created")
    void listen(String inMessage) throws JsonProcessingException {
        try {
            ObjectReader reader = mapper
                    .readerFor(OrderMessageDto.class)
                    .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            OrderMessageDto order = reader.readValue(inMessage);
            if (consumedEventRepository.existsById(order.eventId())) {
                log.info("Duplicate event detected, skipping.");
                return;
            }
            ConsumedEvent consumedEvent = new ConsumedEvent(order.eventId(), Instant.now());
            consumedEventRepository.save(consumedEvent);

            paymentService.createPendingFromOrder(order);


        } catch (JsonProcessingException e) {
            log.error("Failed to parse order.created payload. Message: {}\nError: {}", inMessage, e.getMessage(), e);
        }

    }
}
