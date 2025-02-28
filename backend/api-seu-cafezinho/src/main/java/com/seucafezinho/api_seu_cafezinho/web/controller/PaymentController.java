package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.entity.Payment;
import com.seucafezinho.api_seu_cafezinho.service.PaymentService;
import com.seucafezinho.api_seu_cafezinho.web.dto.PaymentRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders/{orderId}/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(
            @PathVariable UUID orderId,
            @PathVariable UUID paymentId) {
        PaymentResponseDto payment = paymentService.findByIdAndOrder(orderId, paymentId);
        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentResponseDto>> getPaymentsByOrder(
            @PathVariable UUID orderId,
            Pageable pageable) {
        Page<PaymentResponseDto> payments = paymentService.findAllByOrder(orderId, pageable);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/status/{paymentStatus}")
    public ResponseEntity<Page<PaymentResponseDto>> getPaymentsByStatus(
            @PathVariable UUID orderId,
            @PathVariable Payment.PaymentStatus paymentStatus,
            Pageable pageable) {
        Page<PaymentResponseDto> payments = paymentService.findByStatus(paymentStatus, pageable);
        return ResponseEntity.ok(payments);
    }

    public ResponseEntity<PaymentResponseDto> createPayment(
            @PathVariable UUID orderId,
            @RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto createdPayment = paymentService.create(paymentRequestDto, orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> updatePayment(
            @PathVariable UUID orderId,
            @PathVariable UUID paymentId,
            @RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto updatedPayment = paymentService.update(orderId, paymentId, paymentRequestDto);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable UUID orderId,
            @PathVariable UUID paymentId) {
        paymentService.delete(orderId, paymentId);
        return ResponseEntity.noContent().build();
    }
}