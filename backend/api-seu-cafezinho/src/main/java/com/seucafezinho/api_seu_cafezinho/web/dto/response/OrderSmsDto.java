package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import com.seucafezinho.api_seu_cafezinho.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderSmsDto {

    private UUID orderId;
    private String userName;
    private String userPhone;
    private OrderStatus status;
}