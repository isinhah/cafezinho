package com.seucafezinho.api_seu_cafezinho.web.mapper;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CategoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(source = "name", target = "name")
    Category toCategory(CategoryRequestDto createDto);

    CategoryResponseDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(source = "name", target = "name")
    void updateCategoryFromDto(CategoryRequestDto updateDto, @MappingTarget Category category);
}