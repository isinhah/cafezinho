package com.seucafezinho.api_seu_cafezinho.web.mapper;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.web.dto.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.CategoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(target = "products", ignore = true)
    Category toCategory(CategoryRequestDto createDto);

    CategoryResponseDto toDto(Category category);
}