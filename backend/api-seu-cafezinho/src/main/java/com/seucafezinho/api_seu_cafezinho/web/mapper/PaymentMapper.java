package com.seucafezinho.api_seu_cafezinho.web.mapper;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.Payment;
import com.seucafezinho.api_seu_cafezinho.web.dto.PaymentRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "paymentType", target = "paymentType")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    @Mapping(source = "amount", target = "amount")
    Payment toPayment(PaymentRequestDto paymentRequestDto);

    @Mapping(source = "order.id", target = "orderId")
    PaymentResponseDto toDto(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "orderId", target = "order.id")
    void updatePaymentFromDto(PaymentRequestDto paymentRequestDto, @MappingTarget Payment payment);

    default Payment toEntity(PaymentRequestDto paymentRequestDto, Order order) {
        Payment payment = toPayment(paymentRequestDto);
        payment.setOrder(order);
        return payment;
    }
}