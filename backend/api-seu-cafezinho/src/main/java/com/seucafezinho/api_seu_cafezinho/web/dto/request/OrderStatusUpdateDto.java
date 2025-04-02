package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateDto {

    @NotNull(message = "Order status cannot be null. Use 'PENDING', 'IN_PROGRESS', 'DELIVERING', 'READY_FOR_PICKUP', 'COMPLETED', or 'CANCELED'.")
    private String status;
}
