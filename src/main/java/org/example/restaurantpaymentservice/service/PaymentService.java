package org.example.restaurantpaymentservice.service;

import lombok.RequiredArgsConstructor;
import org.example.restaurantpaymentservice.authorization.PaymentAuthorizer;
import org.example.restaurantpaymentservice.dto.PaymentCreateRequest;
import org.example.restaurantpaymentservice.enums.PaymentStatus;
import org.example.restaurantpaymentservice.model.Payment;
import org.example.restaurantpaymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentAuthorizer authorizer;
    private final PaymentProducerService producerService;
    private final PaymentRepository repository;

    @Transactional
    public Payment create(PaymentCreateRequest req) {
        var decision = authorizer.authorize(req);

        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .orderId(req.orderId())
                .amount(req.amount())
                .providerPaymentId(req.providerPaymentId())
                .status(decision.authorized() ? PaymentStatus.AUTHORIZED : PaymentStatus.FAILED)
                .failureReason(decision.authorized() ? null : decision.failureReason())
                .createdAt(Instant.now())
                .build();

        Payment saved = repository.save(payment);
        producerService.send(saved);
        return saved;
    }

    @Transactional(readOnly = true)
    public Payment getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Payment> getAll(PaymentStatus status) {
        return (status != null) ? repository.findByStatus(status) : repository.findAll();
    }

    public static class PaymentNotFoundException extends RuntimeException {
        public PaymentNotFoundException(UUID id) { super("Payment not found: " + id); }
    }
}

