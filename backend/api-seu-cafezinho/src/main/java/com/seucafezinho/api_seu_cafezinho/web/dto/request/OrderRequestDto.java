package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de um pedido")
public class OrderRequestDto {

    @NotNull(message = "Delivery method cannot be null. Use 'HOME_DELIVERY' or 'PICKUP'.")
    @Schema(description = "Delivery method for the order. Possible values: 'HOME_DELIVERY' (order delivered to the provided address) or 'PICKUP' (customer picks up the order at the store).",
            example = "HOME_DELIVERY")
    private String deliveryMethod;

    @Schema(description = "Customer Address ID (Required for Home_Delivery)", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID addressId;

    @NotEmpty(message = "The order must contain at least one product.")
    @Schema(description = "List of order items.")
    private List<OrderItemRequestDto> products = new ArrayList<>();

    @NotNull(message = "Payment method cannot be null. Use 'PIX', or 'CASH'.")
    @Schema(description = "Payment method used for the order. Possible values: 'PIX' (payment via PIX) and 'CASH' (cash payment).",
            example = "PIX")
    private String paymentMethod;

    @AssertTrue(message = "Address ID is required for HOME_DELIVERY and must be null for PICKUP.")
    private boolean isValidAddressForHomeDelivery() {
        if ("HOME_DELIVERY".equals(deliveryMethod)) {
            return addressId != null;
        }
        return "PICKUP".equals(deliveryMethod) && addressId == null;
    }
}