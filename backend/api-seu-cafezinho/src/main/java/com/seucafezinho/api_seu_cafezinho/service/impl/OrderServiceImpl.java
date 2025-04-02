package com.seucafezinho.api_seu_cafezinho.service.impl;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.repository.OrderItemRepository;
import com.seucafezinho.api_seu_cafezinho.repository.OrderRepository;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.service.OrderService;
import com.seucafezinho.api_seu_cafezinho.util.OrderFactory;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
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

    @Transactional(readOnly = true)
    public OrderResponseDto findByIdAndUser(UUID userId, UUID orderId) {
        User user = findUserById(userId);
        Order order = findOrderByIdAndUser(orderId, user);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> findAllByUser(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderMapper.INSTANCE::toDto);
    }

    @Transactional
    public OrderResponseDto createOrder(UUID userId, OrderRequestDto orderRequestDto) {
        User user = findUserById(userId);

        Order order = orderFactory.createOrder(user, orderRequestDto);
        order.calculateTotalPrice();

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        return OrderMapper.INSTANCE.toDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto updateOrder(UUID userId, UUID orderId, OrderRequestDto orderRequestDto) {
        User user = findUserById(userId);
        Order order = findOrderByIdAndUser(orderId, user);

        orderFactory.updateOrder(order, orderRequestDto);
        order.calculateTotalPrice();

        Order updatedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        return OrderMapper.INSTANCE.toDto(updatedOrder);
    }

    @Transactional
    public void delete(UUID userId, UUID orderId) {
        User user = findUserById(userId);
        Order order = findOrderByIdAndUser(orderId, user);
        orderRepository.delete(order);
    }

    @Transactional(readOnly = true)
    private Order findOrderByIdAndUser(UUID orderId, User user) {
        return orderRepository.findByIdAndUser(orderId, user)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id: '%s' not found for user: '%s'", orderId, user.getId())
                ));
    }

    @Transactional(readOnly = true)
    private User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id: '%s' not found", userId)));
    }
}