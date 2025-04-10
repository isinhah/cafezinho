package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.producer.SmsProducer;
import com.seucafezinho.api_seu_cafezinho.repository.OrderItemRepository;
import com.seucafezinho.api_seu_cafezinho.repository.OrderRepository;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.service.impl.OrderServiceImpl;
import com.seucafezinho.api_seu_cafezinho.util.OrderConstants;
import com.seucafezinho.api_seu_cafezinho.util.OrderFactory;
import com.seucafezinho.api_seu_cafezinho.util.SecurityContextTestUtil;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER;
import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderFactory orderFactory;

    @Mock
    private SmsProducer smsProducer;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setupSecurityContext() {
        SecurityContextTestUtil.mockAuthenticatedUser(USER_ID.toString());
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextTestUtil.clear();
    }

    @Test
    void findById_ShouldReturnOrderResponseDto_WhenSuccessful() {
        when(orderRepository.findById(OrderConstants.ORDER_ID))
                .thenReturn(Optional.of(OrderConstants.ORDER));

        OrderResponseDto response = orderService.findById(OrderConstants.ORDER_ID);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(OrderConstants.ORDER_RESPONSE_DTO);

        verify(orderRepository).findById(OrderConstants.ORDER_ID);
    }

    @Test
    void findAll_ShouldReturnPageOfOrderResponseDto_WhenSuccessful() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> orders = new PageImpl<>(List.of(OrderConstants.ORDER));

        when(orderRepository.findAll(pageable)).thenReturn(orders);

        Page<OrderResponseDto> result = orderService.findAll(pageable);

        assertThat(result.getContent())
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(OrderConstants.ORDER_RESPONSE_DTO);

        verify(orderRepository).findAll(pageable);
    }

    @Test
    void findAllByUserId_ShouldReturnPageOfOrderResponseDto_WhenSuccessful() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> orders = new PageImpl<>(List.of(OrderConstants.ORDER));

        when(orderRepository.findAllByUserId(USER_ID, pageable))
                .thenReturn(orders);

        Page<OrderResponseDto> result = orderService.findAllByUserId(USER_ID, pageable);

        assertThat(result.getContent())
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(OrderConstants.ORDER_RESPONSE_DTO);

        verify(orderRepository).findAllByUserId(USER_ID, pageable);
    }

    @Test
    void createOrder_ShouldSaveAndReturnOrderResponseDto_WhenSuccessful() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(USER));

        when(orderFactory.createOrder(USER, OrderConstants.ORDER_REQUEST_DTO))
                .thenReturn(OrderConstants.ORDER);

        when(orderRepository.save(OrderConstants.ORDER))
                .thenReturn(OrderConstants.ORDER);

        OrderResponseDto response = orderService.createOrder(USER_ID, OrderConstants.ORDER_REQUEST_DTO);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(OrderConstants.ORDER_RESPONSE_DTO);

        verify(orderRepository).save(OrderConstants.ORDER);
        verify(orderItemRepository).saveAll(OrderConstants.ORDER.getOrderItems());
        verify(smsProducer).publishOrderSms(OrderConstants.ORDER);
    }

    @Test
    void updateOrder_ShouldUpdateAndReturnOrderResponseDto_WhenSuccessful() {
        when(orderRepository.findById(OrderConstants.ORDER_ID))
                .thenReturn(Optional.of(OrderConstants.ORDER));

        doNothing().when(orderFactory).updateOrder(OrderConstants.ORDER, OrderConstants.ORDER_REQUEST_DTO);
        when(orderRepository.save(OrderConstants.ORDER)).thenReturn(OrderConstants.ORDER);

        OrderResponseDto response = orderService.updateOrder(OrderConstants.ORDER_ID, OrderConstants.ORDER_REQUEST_DTO);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(OrderConstants.ORDER_RESPONSE_DTO);

        verify(orderRepository).save(OrderConstants.ORDER);
        verify(orderItemRepository).saveAll(OrderConstants.ORDER.getOrderItems());
        verify(smsProducer).publishOrderSms(OrderConstants.ORDER);
    }

    @Test
    void updateOrderStatus_ShouldUpdateStatusAndReturnOrderResponseDto_WhenSuccessful() {
        when(orderRepository.findById(OrderConstants.ORDER_ID))
                .thenReturn(Optional.of(OrderConstants.ORDER));

        when(orderRepository.save(OrderConstants.ORDER)).thenReturn(OrderConstants.ORDER);

        OrderResponseDto response = orderService.updateOrderStatus(OrderConstants.ORDER_ID, OrderConstants.STATUS_UPDATE_DTO);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(OrderConstants.ORDER_RESPONSE_DTO);

        verify(orderRepository).save(OrderConstants.ORDER);
        verify(smsProducer).publishOrderSms(OrderConstants.ORDER);
    }

    @Test
    void delete_ShouldRemoveOrder_WhenSuccessful() {
        when(orderRepository.findById(OrderConstants.ORDER_ID))
                .thenReturn(Optional.of(OrderConstants.ORDER));

        orderService.delete(OrderConstants.ORDER_ID);

        verify(orderRepository).delete(OrderConstants.ORDER);
    }
}