package com.seucafezinho.api_seu_cafezinho.service.impl;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.entity.Product;
import com.seucafezinho.api_seu_cafezinho.repository.CategoryRepository;
import com.seucafezinho.api_seu_cafezinho.repository.ProductRepository;
import com.seucafezinho.api_seu_cafezinho.service.ProductService;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.ProductRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.ProductResponseDto;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long productId) {
        Product product = findProductById(productId);
        return ProductMapper.INSTANCE.toDto(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper.INSTANCE::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAllByCategory(Long categoryId, Pageable pageable) {
        Category category = findCategoryById(categoryId);
        return productRepository.findAllByCategory(category, pageable)
                .map(ProductMapper.INSTANCE::toDto);
    }

    @Transactional
    public ProductResponseDto addProductToCategory(Long categoryId, Long productId) {
        Category category = findCategoryById(categoryId);
        Product product = findProductById(productId);

        category.getProducts().add(product);

        product.setCategory(category);

        categoryRepository.save(category);

        return ProductMapper.INSTANCE.toDto(product);
    }

    @Transactional
    public ProductResponseDto create(ProductRequestDto createDto) {
        Product product = ProductMapper.INSTANCE.toEntity(createDto, null);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDto(savedProduct);
    }

    @Transactional
    public ProductResponseDto update(Long productId, ProductRequestDto updateDto) {
        Product existingProduct = findProductById(productId);

        if (productRepository.existsByNameIgnoreCaseAndIdNot(updateDto.getName(), productId)) {
            throw new UniqueViolationException(String.format("Another product with the name: '%s' already exists", updateDto.getName()));
        }

        ProductMapper.INSTANCE.updateProductFromDto(updateDto, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.INSTANCE.toDto(updatedProduct);
    }

    @Transactional
    public void delete(Long productId) {
        Product product = findProductById(productId);
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Product with id: '%s' not found", productId)
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