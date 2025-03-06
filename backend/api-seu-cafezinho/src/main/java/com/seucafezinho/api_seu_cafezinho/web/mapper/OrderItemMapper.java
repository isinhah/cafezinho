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
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(source = "createDto.quantity", target = "unitQuantity")
    @Mapping(source = "product.price", target = "unitPrice")
    @Mapping(source = "product.id", target = "product", qualifiedByName = "mapProduct")
    OrderItem toOrderItem(OrderItemRequestDto createDto, Product product);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(target = "quantity", source = "unitQuantity")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @Named("mapProduct")
    default Product mapProduct(Long productId) {
        if (productId == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productId);
        return product;
    }
}