package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.Payment;
import com.seucafezinho.api_seu_cafezinho.repository.OrderRepository;
import com.seucafezinho.api_seu_cafezinho.repository.PaymentRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.PaymentRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.PaymentResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public PaymentResponseDto findByIdAndOrder(UUID orderId, UUID paymentId) {
        Order order = findOrderById(orderId);
        Payment payment = findPaymentByIdAndOrder(paymentId, order);
        return PaymentMapper.INSTANCE.toDto(payment);
    }

    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> findAllByOrder(UUID orderId, Pageable pageable) {
        Order order = findOrderById(orderId);
        return paymentRepository.findAllByOrder(order, pageable)
                .map(PaymentMapper.INSTANCE::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> findByStatus(Payment.PaymentStatus paymentStatus, Pageable pageable) {
        return paymentRepository.findAllByPaymentStatus(paymentStatus, pageable)
                .map(PaymentMapper.INSTANCE::toDto);
    }

    @Transactional
    public PaymentResponseDto create(PaymentRequestDto createDto, UUID orderId) {
        Order order = findOrderById(orderId);
        Payment payment = PaymentMapper.INSTANCE.toEntity(createDto, order);
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentMapper.INSTANCE.toDto(savedPayment);
    }

    @Transactional
    public PaymentResponseDto update(UUID orderId, UUID paymentId, PaymentRequestDto updateDto) {
        Order order = findOrderById(orderId);
        Payment payment = findPaymentByIdAndOrder(paymentId, order);

        PaymentMapper.INSTANCE.updatePaymentFromDto(updateDto, payment);

        Payment updatedPayment = paymentRepository.save(payment);
        return PaymentMapper.INSTANCE.toDto(updatedPayment);
    }

    @Transactional
    public void delete(UUID orderId, UUID paymentId) {
        Order order = findOrderById(orderId);
        Payment payment = findPaymentByIdAndOrder(paymentId, order);
        paymentRepository.delete(payment);
    }

    @Transactional(readOnly = true)
    private Payment findPaymentByIdAndOrder(UUID paymentId, Order order) {
        return paymentRepository.findByIdAndOrder(paymentId, order)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Payment with id: '%s' not found for order: '%s'", paymentId, order.getId())
                ));
    }

    @Transactional(readOnly = true)
    private Order findOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException(String.format("Order with id: '%s' not found", orderId)));
    }
}