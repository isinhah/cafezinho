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
import org.mapstruct.MappingTarget;
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
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "deliveryMethod", target = "deliveryMethod")
    @Mapping(source = "addressId", target = "address", qualifiedByName = "mapAddress")
    @Mapping(source = "items", target = "orderItems", qualifiedByName = "mapOrderItems")
    Order toOrder(OrderRequestDto createDto);

    @Mapping(source = "user.name", target = "user")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "orderItems", target = "items", qualifiedByName = "mapOrderItemDtos")
    OrderResponseDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "addressId", target = "address", qualifiedByName = "mapAddress")
    @Mapping(source = "items", target = "orderItems", qualifiedByName = "mapOrderItems")
    void updateOrderFromDto(OrderRequestDto updateDto, @MappingTarget Order order);

    @Named("mapAddress")
    default Address mapAddress(UUID addressId) {
        if (addressId == null) {
            return null;
        }
        Address address = new Address();
        address.setId(addressId);
        return address;
    }

    @Named("mapOrderItem")
    default OrderItem mapOrderItem(OrderItemRequestDto itemDto) {
        if (itemDto == null) {
            return null;
        }

        OrderItem item = new OrderItem();
        item.setUnitQuantity(itemDto.getQuantity());

        return item;
    }

    default OrderItemResponseDto mapOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemResponseDto itemDto = new OrderItemResponseDto();
        itemDto.setProductName(orderItem.getProduct().getName());
        itemDto.setQuantity(orderItem.getUnitQuantity());
        itemDto.setUnitPrice(orderItem.getUnitPrice());
        itemDto.setTotalPrice(orderItem.getTotalPrice());
        return itemDto;
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

    @Named("mapOrderItemDtos")
    default List<OrderItemResponseDto> mapOrderItemDtos(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return null;
        }
        return orderItems.stream()
                .map(this::mapOrderItemDto)
                .collect(Collectors.toList());
    }
}