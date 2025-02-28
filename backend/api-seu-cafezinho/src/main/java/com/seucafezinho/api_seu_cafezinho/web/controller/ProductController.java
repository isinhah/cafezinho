package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.service.ProductService;
import com.seucafezinho.api_seu_cafezinho.web.dto.ProductRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.ProductResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories/{categoryId}/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        ProductResponseDto product = productService.findByIdAndCategory(productId, categoryId);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getProductsByCategory(
            @PathVariable Long categoryId,
            Pageable pageable) {
        Page<ProductResponseDto> products = productService.findAllByCategory(categoryId, pageable);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @PathVariable Long categoryId,
            @Valid @RequestBody ProductRequestDto createDto) {
        ProductResponseDto newProduct = productService.create(createDto, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long categoryId,
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequestDto updateDto) {
        ProductResponseDto existingProduct = productService.update(categoryId, productId, updateDto);
        return ResponseEntity.ok(existingProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        productService.delete(productId, categoryId);
        return ResponseEntity.noContent().build();
    }
}