package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.entity.Product;
import com.seucafezinho.api_seu_cafezinho.repository.CategoryRepository;
import com.seucafezinho.api_seu_cafezinho.repository.ProductRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.ProductRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.ProductResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.exception.UniqueViolationException;
import com.seucafezinho.api_seu_cafezinho.web.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponseDto findByIdAndCategory(Long categoryId, Long productId) {
        Category category = findCategoryById(categoryId);
        Product product = findProductByIdAndCategory(productId, category);
        return ProductMapper.INSTANCE.toDto(product);
    }

    public Page<ProductResponseDto> findAllByCategory(Long categoryId, Pageable pageable) {
        Category category = findCategoryById(categoryId);
        return productRepository.findAllByCategory(category, pageable)
                .map(ProductMapper.INSTANCE::toDto);
    }

    @Transactional
    public ProductResponseDto create(ProductRequestDto createDto, Long categoryId) {
        Category category = findCategoryById(categoryId);

        Product product = ProductMapper.INSTANCE.toEntity(createDto, category);

        Product savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDto(savedProduct);
    }

    @Transactional
    public ProductResponseDto update(Long categoryId, Long productId, ProductRequestDto updateDto) {
        Category category = findCategoryById(categoryId);
        Product existingProduct = findProductByIdAndCategory(productId, category);

        if (productRepository.existsByCategoryAndNameIgnoreCase(category, updateDto.getName())
                && !existingProduct.getName().equalsIgnoreCase(updateDto.getName())) {
            throw new UniqueViolationException(String.format("Another product with the name: '%s' already exists for this category", updateDto.getName()));
        }

        ProductMapper.INSTANCE.updateProductFromDto(updateDto, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.INSTANCE.toDto(updatedProduct);
    }

    @Transactional
    public void delete(Long categoryId, Long productId) {
        Category category = findCategoryById(categoryId);
        Product product = findProductByIdAndCategory(productId, category);
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    private Product findProductByIdAndCategory(Long productId, Category category) {
        return productRepository.findByIdAndCategory(productId, category)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Product with id: '%s' not found for category: '%s'", productId, category.getId())
                ));
    }

    @Transactional(readOnly = true)
    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Category with id: '%s' not found", categoryId)
                ));
    }
}