package com.seucafezinho.api_seu_cafezinho.web.mapper;

import com.seucafezinho.api_seu_cafezinho.entity.OrderItem;
import com.seucafezinho.api_seu_cafezinho.entity.Product;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderItemRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(source = "product.price", target = "unitPrice")
    @Mapping(source = "createDto.productQuantity", target = "productQuantity")
    @Mapping(source = "product", target = "product")
    OrderItem toOrderItem(OrderItemRequestDto createDto, Product product);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "productQuantity", target = "quantity")
    OrderItemResponseDto toDto(OrderItem orderItem);
}