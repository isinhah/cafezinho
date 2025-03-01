package com.seucafezinho.api_seu_cafezinho.web.dto;

import com.seucafezinho.api_seu_cafezinho.entity.Payment;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    @NotNull(message = "Order ID cannot be null")
    private UUID orderId;

    @NotNull(message = "Payment type cannot be null")
    private Payment.PaymentType paymentType;

    @NotNull(message = "Payment status cannot be null")
    private Payment.PaymentStatus paymentStatus;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}