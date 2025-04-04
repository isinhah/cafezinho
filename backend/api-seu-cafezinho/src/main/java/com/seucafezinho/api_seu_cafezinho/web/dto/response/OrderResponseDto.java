package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO para representar a resposta de um pedido")
public class OrderResponseDto {

    @Schema(description = "Unique identifier of the order.", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Customer Address ID (only applicable for 'HOME_DELIVERY').", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID addressId;

    @Schema(description = "Delivery method for the order. Possible values: 'HOME_DELIVERY' (delivered to the provided address) or 'PICKUP' (customer picks up the order at the store).",
            example = "HOME_DELIVERY")
    private String deliveryMethod;

    @Schema(description = "Payment method used for the order. Possible values: 'PIX' (payment via PIX) and 'CASH' (cash payment).",
            example = "PIX")
    private String paymentMethod;

    @Schema(description = "Name of the customer who placed the order.", example = "John Doe")
    private String name;

    @Schema(description = "Phone number of the customer who placed the order.", example = "+5511987654321")
    private String phone;

    @Schema(description = "Current status of the order. Possible values: 'PENDING', 'CONFIRMED', 'CANCELED'.",
            example = "CONFIRMED")
    private String status;

    @Schema(description = "Total price of the order.", example = "29.99")
    private BigDecimal totalPrice;

    @Schema(description = "List of items included in the order.")
    private List<OrderItemResponseDto> items;
}