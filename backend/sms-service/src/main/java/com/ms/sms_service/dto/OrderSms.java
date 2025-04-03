package com.ms.sms_service.dto;

import com.ms.sms_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderSms {

    private UUID orderId;
    private String userName;
    private String userPhone;
    private OrderStatus status;
}