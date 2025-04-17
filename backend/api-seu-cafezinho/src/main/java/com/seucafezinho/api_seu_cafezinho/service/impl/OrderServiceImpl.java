package com.seucafezinho.api_seu_cafezinho.service.impl;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.entity.enums.OrderStatus;
import com.seucafezinho.api_seu_cafezinho.producer.SmsProducer;
import com.seucafezinho.api_seu_cafezinho.repository.OrderItemRepository;
import com.seucafezinho.api_seu_cafezinho.repository.OrderRepository;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.service.OrderService;
import com.seucafezinho.api_seu_cafezinho.factory.OrderFactory;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderStatusUpdateDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final OrderFactory orderFactory;
    private final SmsProducer smsProducer;

    @Transactional(readOnly = true)
    public OrderResponseDto findById(UUID orderId) {
        Order order = findOrderById(orderId);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderMapper.INSTANCE::toDto);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> findAllByUserId(UUID userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable)
                .map(OrderMapper.INSTANCE::toDto);
    }

    @Transactional
    public OrderResponseDto createOrder(UUID userId, OrderRequestDto orderRequestDto) {
        User user = findUserById(userId);

        Order order = orderFactory.createOrder(user, orderRequestDto);
        order.calculateTotalPrice();

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        smsProducer.publishOrderSms(savedOrder);

        return OrderMapper.INSTANCE.toDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto updateOrder(UUID orderId, OrderRequestDto orderRequestDto) {
        Order order = findOrderById(orderId);

        orderFactory.updateOrder(order, orderRequestDto);
        order.calculateTotalPrice();

        Order updatedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        smsProducer.publishOrderSms(updatedOrder);

        return OrderMapper.INSTANCE.toDto(updatedOrder);
    }

    @Transactional
    public OrderResponseDto updateOrderStatus(UUID orderId, OrderStatusUpdateDto statusUpdateDto) {
        Order order = findOrderById(orderId);
        OrderStatus newStatus = OrderStatus.valueOf(statusUpdateDto.getStatus());

        if (newStatus == OrderStatus.DELIVERING && order.getAddress() == null) {
            throw new IllegalStateException("Cannot set status to DELIVERING for orders without an address. The customer must pick up the order.");
        }

        if (newStatus == OrderStatus.READY_FOR_PICKUP && order.getAddress() != null) {
            throw new IllegalStateException("Order can only be marked as READY_FOR_PICKUP if no delivery address is set.");
        }

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        smsProducer.publishOrderSms(updatedOrder);

        return OrderMapper.INSTANCE.toDto(updatedOrder);
    }

    @Transactional
    public void delete(UUID orderId) {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
    }

    @Transactional(readOnly = true)
    private Order findOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id: '%s' not found", orderId)));
    }

    @Transactional(readOnly = true)
    private User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id: '%s' not found", userId)));
    }
}