package com.seucafezinho.api_seu_cafezinho.web.mapper;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.entity.Product;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.ProductRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    Product toProduct(ProductRequestDto createDto);

    @Mapping(source = "category.id", target = "categoryId")
    ProductResponseDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    void updateProductFromDto(ProductRequestDto updateDto, @MappingTarget Product product);

    default Product toEntity(ProductRequestDto dto, Category category) {
        Product product = toProduct(dto);
        product.setCategory(category);
        return product;
    }
}