package com.seucafezinho.api_seu_cafezinho.web.mapper;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.Payment;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.PaymentRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "paymentType", target = "paymentType")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    @Mapping(source = "amount", target = "amount")
    Payment toPayment(PaymentRequestDto createDto);

    @Mapping(source = "order.id", target = "orderId")
    PaymentResponseDto toDto(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    void updatePaymentFromDto(PaymentRequestDto updateDto, @MappingTarget Payment payment);

    default Payment toEntity(PaymentRequestDto dto, Order order) {
        Payment payment = toPayment(dto);
        payment.setOrder(order);
        return payment;
    }
}