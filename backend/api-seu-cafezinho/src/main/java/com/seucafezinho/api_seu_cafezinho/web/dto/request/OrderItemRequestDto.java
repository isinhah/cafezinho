package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para adicionar um item ao pedido")
public class OrderItemRequestDto {

    @NotNull(message = "Product ID cannot be null")
    @Schema(description = "Unique identifier of the product.", example = "1")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    @Schema(description = "Quantity of the product in the order.", example = "2")
    private Integer productQuantity;
}