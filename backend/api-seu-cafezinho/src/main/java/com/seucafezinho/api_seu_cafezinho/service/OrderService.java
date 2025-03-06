package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.*;
import com.seucafezinho.api_seu_cafezinho.repository.AddressRepository;
import com.seucafezinho.api_seu_cafezinho.repository.OrderRepository;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.exception.BusinessException;
import com.seucafezinho.api_seu_cafezinho.web.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PaymentService paymentService;

    @Transactional(readOnly = true)
    public OrderResponseDto findByIdAndUser(UUID userId, UUID orderId) {
        User user = findUserById(userId);
        Order order = findOrderByIdAndUser(orderId, user);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Transactional
    public OrderResponseDto create(UUID userId, OrderRequestDto orderDto) {
        User user = findUserById(userId);

        Address address = validateAddress(orderDto, user);
        validateOrderRules(orderDto, address);

        Order order = OrderMapper.INSTANCE.toOrder(orderDto);
        order.setUser(user);
        order.setAddress(address);

        List<OrderItem> orderItems = OrderMapper.INSTANCE.mapOrderItems(orderDto.getItems());
        order.setOrderItems(orderItems);

        order.calculateTotalPrice();

        Order savedOrder = orderRepository.save(order);
        paymentService.createPaymentForOrder(savedOrder);

        return OrderMapper.INSTANCE.toDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto update(UUID userId, UUID orderId, OrderRequestDto orderDto) {
        User user = findUserById(userId);
        Order order = findOrderByIdAndUser(orderId, user);

        Address address = validateAddress(orderDto, user);
        validateOrderRules(orderDto, address);

        OrderMapper.INSTANCE.updateOrderFromDto(orderDto, order);

        List<OrderItem> updatedItems = OrderMapper.INSTANCE.mapOrderItems(orderDto.getItems());
        order.setOrderItems(updatedItems);

        order.calculateTotalPrice();

        Order updatedOrder = orderRepository.save(order);

        if (updatedOrder.getPayment() == null) {
            paymentService.createPaymentForOrder(updatedOrder);
        }

        return OrderMapper.INSTANCE.toDto(updatedOrder);
    }

    private void validateOrderRules(OrderRequestDto dto, Address address) {
        if (dto.getDeliveryMethod() == Order.DeliveryMethod.HOME_DELIVERY &&
                dto.getStatus() == Order.OrderStatus.READY_FOR_PICKUP) {
            throw new BusinessException("Cannot have status READY_FOR_PICKUP with HOME_DELIVERY method.");
        }
    }

    private Address validateAddress(OrderRequestDto dto, User user) {
        if (dto.getDeliveryMethod() == Order.DeliveryMethod.HOME_DELIVERY) {
            return addressRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new BusinessException("User must provide an address for HOME_DELIVERY."));
        }
        return null;
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