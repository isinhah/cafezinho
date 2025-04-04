package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import com.seucafezinho.api_seu_cafezinho.entity.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar um item em um pedido")
public class OrderItemResponseDto {

    @Schema(description = "Unique identifier of the product.", example = "1")
    private Long productId;

    @Schema(description = "Name of the product.", example = "California Roll")
    private String name;

    @Schema(description = "Quantity of the product in the order.", example = "2")
    private Integer quantity;

    @Schema(description = "Unit price of the product.", example = "8.99")
    private BigDecimal unitPrice;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();
        this.name = orderItem.getProduct().getName();
        this.quantity = orderItem.getProductQuantity();
        this.unitPrice = orderItem.getProduct().getPrice();
    }
}