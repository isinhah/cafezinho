package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import com.seucafezinho.api_seu_cafezinho.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {

    private Long productId;
    private String name;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();
        this.name = orderItem.getProduct().getName();
        this.quantity = orderItem.getProductQuantity();
        this.unitPrice = orderItem.getProduct().getPrice();
    }
}