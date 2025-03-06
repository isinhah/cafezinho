package com.seucafezinho.api_seu_cafezinho.repository;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByIdAndOrder(UUID id, Order order);

    Page<Payment> findAllByOrder(Order order, Pageable pageable);

    Page<Payment> findAllByPaymentStatus(Payment.PaymentStatus paymentStatus, Pageable pageable);

    Optional<Payment> findByOrderId(UUID orderId);
}