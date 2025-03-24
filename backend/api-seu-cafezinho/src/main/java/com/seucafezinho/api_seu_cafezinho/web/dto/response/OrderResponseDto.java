package com.seucafezinho.api_seu_cafezinho.web.dto.response;

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
    private UUID addressId;
    private String deliveryMethod;
    private String paymentMethod;
    private String name;
    private String phone;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemResponseDto> items;
}