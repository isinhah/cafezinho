package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private UUID id;
    private String user;
    private UUID addressId;
    private BigDecimal totalPrice;
    private Order.OrderStatus status;
    private Order.DeliveryMethod deliveryMethod;
    private List<OrderItemResponseDto> items;
}