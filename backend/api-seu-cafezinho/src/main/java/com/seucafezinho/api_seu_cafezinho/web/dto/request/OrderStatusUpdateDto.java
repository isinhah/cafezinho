package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para os administradores atualizarem um pedido existente")
public class OrderStatusUpdateDto {

    @NotNull(message = "Order status cannot be null. Use 'PENDING', 'IN_PROGRESS', 'DELIVERING', 'READY_FOR_PICKUP', 'COMPLETED', or 'CANCELED'.")
    @Schema(description = "Status of the order. Possible values: 'PENDING' (order received), 'IN_PROGRESS' (being prepared), 'DELIVERING' (out for delivery), 'READY_FOR_PICKUP' (ready for pickup), 'COMPLETED' (delivered or picked up), and 'CANCELED' (order canceled).",
            example = "IN_PROGRESS")
    private String status;
}