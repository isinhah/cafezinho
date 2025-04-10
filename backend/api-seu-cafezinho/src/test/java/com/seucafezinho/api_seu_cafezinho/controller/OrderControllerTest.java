package com.seucafezinho.api_seu_cafezinho.controller;

import com.seucafezinho.api_seu_cafezinho.service.OrderService;
import com.seucafezinho.api_seu_cafezinho.web.controller.OrderController;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static com.seucafezinho.api_seu_cafezinho.util.OrderConstants.ORDER_REQUEST_DTO;
import static com.seucafezinho.api_seu_cafezinho.util.OrderConstants.ORDER_RESPONSE_DTO;
import static com.seucafezinho.api_seu_cafezinho.util.OrderConstants.STATUS_UPDATE_DTO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testGetOrderById_WhenSuccessful() {
        UUID orderId = UUID.randomUUID();

        when(orderService.findById(orderId)).thenReturn(ORDER_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            orderController.getOrderById(orderId);
        });

        verify(orderService, times(1)).findById(orderId);
    }

    @Test
    void testGetAllOrders_WhenSuccessful() {
        Pageable pageable = PageRequest.of(0, 10);
        List<OrderResponseDto> orders = List.of(ORDER_RESPONSE_DTO);
        Page<OrderResponseDto> page = new PageImpl<>(orders);

        when(orderService.findAll(pageable)).thenReturn(page);

        assertDoesNotThrow(() -> {
            orderController.getAllOrders(pageable);
        });

        verify(orderService, times(1)).findAll(pageable);
    }

    @Test
    void testGetAllOrdersByUser_WhenSuccessful() {
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        List<OrderResponseDto> orders = List.of(ORDER_RESPONSE_DTO);
        Page<OrderResponseDto> page = new PageImpl<>(orders);

        when(orderService.findAllByUserId(userId, pageable)).thenReturn(page);

        assertDoesNotThrow(() -> {
            orderController.getAllOrdersByUser(userId, pageable);
        });

        verify(orderService, times(1)).findAllByUserId(userId, pageable);
    }

    @Test
    void testCreateOrder_WhenSuccessful() {
        UUID userId = UUID.randomUUID();

        when(orderService.createOrder(userId, ORDER_REQUEST_DTO)).thenReturn(ORDER_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            orderController.createOrder(userId, ORDER_REQUEST_DTO);
        });

        verify(orderService, times(1)).createOrder(userId, ORDER_REQUEST_DTO);
    }

    @Test
    void testUpdateOrder_WhenSuccessful() {
        UUID orderId = UUID.randomUUID();

        when(orderService.updateOrder(orderId, ORDER_REQUEST_DTO)).thenReturn(ORDER_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            orderController.updateOrder(orderId, ORDER_REQUEST_DTO);
        });

        verify(orderService, times(1)).updateOrder(orderId, ORDER_REQUEST_DTO);
    }

    @Test
    void testUpdateOrderStatus_WhenSuccessful() {
        UUID orderId = UUID.randomUUID();

        when(orderService.updateOrderStatus(orderId, STATUS_UPDATE_DTO)).thenReturn(ORDER_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            orderController.updateOrderStatus(orderId, STATUS_UPDATE_DTO);
        });

        verify(orderService, times(1)).updateOrderStatus(orderId, STATUS_UPDATE_DTO);
    }

    @Test
    void testDeleteOrder_WhenSuccessful() {
        UUID orderId = UUID.randomUUID();

        doNothing().when(orderService).delete(orderId);

        assertDoesNotThrow(() -> {
            orderController.deleteOrder(orderId);
        });

        verify(orderService, times(1)).delete(orderId);
    }
}
