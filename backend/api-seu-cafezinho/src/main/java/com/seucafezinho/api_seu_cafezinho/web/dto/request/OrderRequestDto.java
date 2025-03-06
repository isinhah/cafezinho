package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotNull(message = "Address ID cannot be null")
    private UUID addressId;

    @NotNull(message = "Order status cannot be null")
    private Order.OrderStatus status;

    @NotNull(message = "Delivery method cannot be null")
    private Order.DeliveryMethod deliveryMethod;

    private List<OrderItemRequestDto> items;
}