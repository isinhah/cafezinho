package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponseDto findById(Long id);

    Page<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto findByName(String name);

    CategoryResponseDto create(CategoryRequestDto createDto);

    CategoryResponseDto update(Long id, CategoryRequestDto updateDto);

    void delete(Long id);
}