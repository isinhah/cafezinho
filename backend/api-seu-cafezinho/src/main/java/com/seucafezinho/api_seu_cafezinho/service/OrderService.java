package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.OrderItem;
import com.seucafezinho.api_seu_cafezinho.entity.Product;
import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.repository.AddressRepository;
import com.seucafezinho.api_seu_cafezinho.repository.OrderItemRepository;
import com.seucafezinho.api_seu_cafezinho.repository.OrderRepository;
import com.seucafezinho.api_seu_cafezinho.repository.ProductRepository;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public OrderResponseDto findByIdAndUser(UUID userId, UUID orderId) {
        User user = findUserById(userId);
        Order order = findOrderByIdAndUser(orderId, user);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderMapper.INSTANCE::toDto);
    }

    @Transactional
    public OrderResponseDto createOrder(UUID userId, OrderRequestDto orderRequestDto) {
        User user = findUserById(userId);

        Order order = Order.builder()
                .user(user)
                .status(Order.OrderStatus.PENDING)
                .deliveryMethod(Order.DeliveryMethod.valueOf(orderRequestDto.getDeliveryMethod()))
                .paymentMethod(Order.PaymentMethod.valueOf(orderRequestDto.getPaymentMethod()))
                .totalPrice(BigDecimal.ZERO)
                .build();

        if (orderRequestDto.getDeliveryMethod().equalsIgnoreCase("HOME_DELIVERY")) {
            Address address = findAddressById(orderRequestDto.getAddressId());
            order.setAddress(address);
        }

        List<OrderItem> orderItems = orderRequestDto.getProducts().stream()
                .map(itemDto -> {
                    Product product = findProductById(itemDto.getProductId());
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setProductQuantity(itemDto.getProductQuantity());
                    orderItem.setUnitPrice(product.getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        order.calculateTotalPrice();

        Order savedOrder = orderRepository.save(order);

        orderItemRepository.saveAll(orderItems);

        return OrderMapper.INSTANCE.toDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto updateOrder(UUID userId, UUID orderId, OrderRequestDto orderRequestDto) {
        User user = findUserById(userId);
        Order order = findOrderByIdAndUser(orderId, user);

        order.setDeliveryMethod(Order.DeliveryMethod.valueOf(orderRequestDto.getDeliveryMethod()));
        order.setPaymentMethod(Order.PaymentMethod.valueOf(orderRequestDto.getPaymentMethod()));

        if (orderRequestDto.getDeliveryMethod().equalsIgnoreCase("HOME_DELIVERY")) {
            Address address = findAddressById(orderRequestDto.getAddressId());
            order.setAddress(address);
        }

        List<OrderItem> orderItems = orderRequestDto.getProducts().stream()
                .map(itemDto -> {
                    Product product = findProductById(itemDto.getProductId());
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setProductQuantity(itemDto.getProductQuantity());
                    orderItem.setUnitPrice(product.getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        order.calculateTotalPrice();

        Order updatedOrder = orderRepository.save(order);

        orderItemRepository.saveAll(orderItems);

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

    @Transactional(readOnly = true)
    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Transactional(readOnly = true)
    private Address findAddressById(UUID addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }
}