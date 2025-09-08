package org.example.restaurantpaymentservice.dto;

import org.antlr.v4.runtime.misc.NotNull;
import org.example.restaurantpaymentservice.enums.OrderStatus;
import java.time.Instant;
import java.util.UUID;

public record OrderEvent(

) {

    void validate(){

    }
}