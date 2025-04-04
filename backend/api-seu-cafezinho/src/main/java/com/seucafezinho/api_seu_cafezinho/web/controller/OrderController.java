package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.documentation.OrderControllerDocs;
import com.seucafezinho.api_seu_cafezinho.service.OrderService;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderStatusUpdateDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CustomPageResponse;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController implements OrderControllerDocs {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID orderId) {
        OrderResponseDto order = orderService.findById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<CustomPageResponse<OrderResponseDto>> getAllOrders(Pageable pageable) {
        Page<OrderResponseDto> page = orderService.findAll(pageable);
        return ResponseEntity.ok(new CustomPageResponse<>(page));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CustomPageResponse<OrderResponseDto>> getAllOrdersByUser(
            @PathVariable UUID userId, Pageable pageable) {
        Page<OrderResponseDto> page = orderService.findAllByUserId(userId, pageable);
        return ResponseEntity.ok(new CustomPageResponse<>(page));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDto> createOrder(
            @PathVariable UUID userId,
            @Valid @RequestBody OrderRequestDto createDto) {
        OrderResponseDto newOrder = orderService.createOrder(userId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderRequestDto updateDto) {
        OrderResponseDto updatedOrder = orderService.updateOrder(orderId, updateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderStatusUpdateDto statusUpdateDto) {
        OrderResponseDto updatedOrder = orderService.updateOrderStatus(orderId, statusUpdateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.delete(orderId);
        return ResponseEntity.noContent().build();
    }
}