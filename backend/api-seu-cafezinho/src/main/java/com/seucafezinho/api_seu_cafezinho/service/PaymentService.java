package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.Payment;
import com.seucafezinho.api_seu_cafezinho.repository.OrderRepository;
import com.seucafezinho.api_seu_cafezinho.repository.PaymentRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.PaymentRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.PaymentResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.exception.BusinessException;
import com.seucafezinho.api_seu_cafezinho.web.mapper.PaymentMapper;
import jakarta.persistence.EntityNotFoundException;
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
    public void validatePaymentStatusForOrder(UUID orderId) {
        Order order = findOrderById(orderId);

        Payment payment = paymentRepository.findByOrderId(order.getId())
                .orElseThrow(() -> new BusinessException("Payment not found for order"));

        Payment.PaymentStatus expectedPaymentStatus = getExpectedPaymentStatus(order.getStatus());

        if (!payment.getPaymentStatus().equals(expectedPaymentStatus)) {
            throw new BusinessException(
                    String.format("Invalid payment status. Expected: %s, but found: %s", expectedPaymentStatus, payment.getPaymentStatus())
            );
        }
    }

    public void createPaymentForOrder(Order order) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
        paymentRepository.save(payment);
    }

    private Payment.PaymentStatus getExpectedPaymentStatus(Order.OrderStatus orderStatus) {
        return switch (orderStatus) {
            case CANCELED -> Payment.PaymentStatus.CANCELLED;
            case PENDING -> Payment.PaymentStatus.PENDING;
            case IN_PROGRESS, DELIVERING, READY_FOR_PICKUP, COMPLETED -> Payment.PaymentStatus.PAID;
            default -> throw new BusinessException(String.format("Unsupported order status: '%s'", orderStatus));
        };
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
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Payment with id: '%s' not found for order: '%s'", paymentId, order.getId())
                ));
    }

    @Transactional(readOnly = true)
    private Order findOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Order with id: '%s' not found", orderId)));
    }
}