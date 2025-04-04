package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import com.seucafezinho.api_seu_cafezinho.entity.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "DTO para enviar notificações via SMS sobre atualizações do pedido")
public class OrderSmsDto {

    @Schema(description = "Unique identifier of the order.", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID orderId;

    @Schema(description = "Name of the user who placed the order.", example = "John Doe")
    private String userName;

    @Schema(description = "User's phone number in E.164 format.", example = "+15551234567")
    private String userPhone;

    @Schema(description = "Current status of the order.", example = "IN_PROGRESS")
    private OrderStatus status;
}