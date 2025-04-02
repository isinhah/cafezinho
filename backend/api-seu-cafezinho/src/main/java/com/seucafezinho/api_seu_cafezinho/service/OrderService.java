package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderStatusUpdateDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {

    OrderResponseDto findById(UUID orderId);

    Page<OrderResponseDto> findAll(Pageable pageable);

    Page<OrderResponseDto> findAllByUserId(UUID userId, Pageable pageable);

    OrderResponseDto createOrder(UUID userId, OrderRequestDto orderRequestDto);

    OrderResponseDto updateOrder(UUID orderId, OrderRequestDto orderRequestDto);

    OrderResponseDto updateOrderStatus(UUID orderId, OrderStatusUpdateDto statusUpdateDto);

    void delete(UUID orderId);
}