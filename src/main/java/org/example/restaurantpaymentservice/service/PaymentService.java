package org.example.restaurantpaymentservice.service;

import lombok.RequiredArgsConstructor;
import org.example.restaurantpaymentservice.dto.PaymentCreateRequest;
import org.example.restaurantpaymentservice.enums.PaymentStatus;
import org.example.restaurantpaymentservice.model.Payment;
import org.example.restaurantpaymentservice.repository.InMemoryPaymentRepository;
import org.example.restaurantpaymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository = new InMemoryPaymentRepository();

    public Payment create(PaymentCreateRequest req) {
        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .orderId(req.getOrderId())
                .amount(req.getAmount())
                .providerPaymentId(req.getProviderPaymentId())
                .status(PaymentStatus.PENDING) // starts pending
                
                .createdAt(Instant.now())
                .build();

        return repository.save(payment);
    }

    public Payment getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    public List<Payment> getAll(PaymentStatus status) {
        if (status != null) {
            return repository.findByStatus(status);
        }
        return repository.findAll();
    }

    public static class PaymentNotFoundException extends RuntimeException {
        public PaymentNotFoundException(UUID id) {
            super("Payment not found: " + id);
        }
    }
}

