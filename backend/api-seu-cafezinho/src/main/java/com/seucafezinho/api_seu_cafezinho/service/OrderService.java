package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {

    OrderResponseDto findByIdAndUser(UUID userId, UUID orderId);

    Page<OrderResponseDto> findAllByUser(Pageable pageable);

    OrderResponseDto createOrder(UUID userId, OrderRequestDto orderRequestDto);

    OrderResponseDto updateOrder(UUID userId, UUID orderId, OrderRequestDto orderRequestDto);

    void delete(UUID userId, UUID orderId);
}
