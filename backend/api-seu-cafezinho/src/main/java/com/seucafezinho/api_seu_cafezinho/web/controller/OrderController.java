package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.service.OrderService;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/{userId}/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable UUID userId,
            @PathVariable UUID orderId) {
        OrderResponseDto order = orderService.findByIdAndUser(userId, orderId);
        return ResponseEntity.ok(order);
    }

    public ResponseEntity<OrderResponseDto> createOrder(
            @PathVariable UUID userId,
            @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto createdOrder = orderService.create(userId, orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable UUID userId,
            @PathVariable UUID orderId,
            @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto updatedOrder = orderService.update(userId, orderId, orderRequestDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable UUID userId,
            @PathVariable UUID orderId) {
        orderService.delete(userId, orderId);
        return ResponseEntity.noContent().build();
    }
}