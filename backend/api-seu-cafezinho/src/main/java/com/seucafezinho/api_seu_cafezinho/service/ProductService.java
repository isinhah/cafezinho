package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.ProductRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponseDto findById(Long productId);

    Page<ProductResponseDto> findAll(Pageable pageable);

    Page<ProductResponseDto> findAllByCategory(Long categoryId, Pageable pageable);

    ProductResponseDto addProductToCategory(Long categoryId, Long productId);

    ProductResponseDto create(ProductRequestDto createDto);

    ProductResponseDto update(Long productId, ProductRequestDto updateDto);

    void delete(Long productId);
}