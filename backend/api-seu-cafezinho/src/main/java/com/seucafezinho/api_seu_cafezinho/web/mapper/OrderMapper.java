package com.seucafezinho.api_seu_cafezinho.web.mapper;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.OrderItem;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderItemRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderItemResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "paymentMethod", target = "paymentMethod", qualifiedByName = "mapPaymentMethod")
    @Mapping(source = "deliveryMethod", target = "deliveryMethod", qualifiedByName = "mapDeliveryMethod")
    @Mapping(source = "addressId", target = "address", qualifiedByName = "mapAddress")
    @Mapping(source = "products", target = "orderItems", qualifiedByName = "mapOrderItems")
    Order toOrder(OrderRequestDto createDto);

    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "paymentMethod", target = "paymentMethod", qualifiedByName = "mapPaymentMethodToString")
    @Mapping(source = "deliveryMethod", target = "deliveryMethod", qualifiedByName = "mapDeliveryMethodToString")
    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatusToString")
    @Mapping(source = "orderItems", target = "items", qualifiedByName = "mapOrderItemDtos")
    @Mapping(source = "totalPrice", target = "totalPrice")
    OrderResponseDto toDto(Order order);

    @Named("mapDeliveryMethod")
    default Order.DeliveryMethod mapDeliveryMethod(String deliveryMethod) {
        if (deliveryMethod != null) {
            return Order.DeliveryMethod.valueOf(deliveryMethod.toUpperCase());
        }
        return null;
    }

    @Named("mapDeliveryMethodToString")
    default String mapDeliveryMethodToString(Order.DeliveryMethod deliveryMethod) {
        return deliveryMethod != null ? deliveryMethod.name() : null;
    }

    @Named("mapPaymentMethod")
    default Order.PaymentMethod mapPaymentMethod(String paymentMethod) {
        if (paymentMethod != null) {
            return Order.PaymentMethod.valueOf(paymentMethod.toUpperCase());
        }
        return null;
    }

    @Named("mapPaymentMethodToString")
    default String mapPaymentMethodToString(Order.PaymentMethod paymentMethod) {
        return paymentMethod != null ? paymentMethod.name() : null;
    }

    @Named("mapStatusToString")
    default String mapStatusToString(Order.OrderStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("mapAddress")
    default Address mapAddress(UUID addressId) {
        if (addressId == null) {
            return null;
        }
        Address address = new Address();
        address.setId(addressId);
        return address;
    }

    @Named("mapOrderItems")
    default List<OrderItem> mapOrderItems(List<OrderItemRequestDto> itemsDto) {
        if (itemsDto == null) {
            return null;
        }
        return itemsDto.stream()
                .map(this::mapOrderItem)
                .collect(Collectors.toList());
    }

    default OrderItem mapOrderItem(OrderItemRequestDto itemDto) {
        if (itemDto == null) {
            return null;
        }
        OrderItem item = new OrderItem();
        item.setProductQuantity(itemDto.getProductQuantity());
        return item;
    }

    @Named("mapOrderItemDtos")
    default List<OrderItemResponseDto> mapOrderItemDtos(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return null;
        }
        return orderItems.stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());
    }
}