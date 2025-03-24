package com.seucafezinho.api_seu_cafezinho.web.dto.request;

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
public class OrderRequestDto {

    @NotNull(message = "Delivery method cannot be null. Use 'HOME_DELIVERY' or 'PICKUP'.")
    private String deliveryMethod;

    private UUID addressId;

    @NotEmpty(message = "The order must contain at least one product.")
    private List<OrderItemRequestDto> products = new ArrayList<>();

    @NotNull(message = "Payment method cannot be null. Use 'PIX', or 'CASH'.")
    private String paymentMethod;

    @AssertTrue(message = "Address ID is required for HOME_DELIVERY and must be null for PICKUP.")
    private boolean isValidAddressForHomeDelivery() {
        if ("HOME_DELIVERY".equals(deliveryMethod)) {
            return addressId != null;
        }
        return "PICKUP".equals(deliveryMethod) && addressId == null;
    }
}