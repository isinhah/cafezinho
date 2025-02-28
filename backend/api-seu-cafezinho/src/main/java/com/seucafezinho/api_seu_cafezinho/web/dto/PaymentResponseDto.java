package com.seucafezinho.api_seu_cafezinho.web.dto;

import com.seucafezinho.api_seu_cafezinho.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {

    private UUID id;
    private UUID orderId;
    private Payment.PaymentType paymentType;
    private Payment.PaymentStatus paymentStatus;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
}