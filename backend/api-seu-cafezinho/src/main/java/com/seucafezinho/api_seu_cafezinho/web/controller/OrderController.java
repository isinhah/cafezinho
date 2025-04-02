package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.service.OrderService;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders/{userId}")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderByUser(
            @PathVariable UUID userId,
            @PathVariable UUID orderId) {
        OrderResponseDto order = orderService.findByIdAndUser(userId, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public Page<OrderResponseDto> getAllOrdersFromUser(Pageable pageable) {
        return orderService.findAllByUser(pageable);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @PathVariable UUID userId,
            @Valid @RequestBody OrderRequestDto createDto) {
        OrderResponseDto newOrder = orderService.createOrder(userId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> alterOrder(
            @PathVariable UUID userId,
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderRequestDto updateDto) {
        OrderResponseDto existingOrder = orderService.updateOrder(userId, orderId, updateDto);
        return ResponseEntity.ok(existingOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable UUID userId,
            @PathVariable UUID orderId) {
        orderService.delete(userId, orderId);
        return ResponseEntity.noContent().build();
    }
}