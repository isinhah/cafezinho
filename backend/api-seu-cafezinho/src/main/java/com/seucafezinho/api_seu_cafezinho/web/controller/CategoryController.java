package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.documentation.CategoryControllerDocs;
import com.seucafezinho.api_seu_cafezinho.service.CategoryService;
import com.seucafezinho.api_seu_cafezinho.service.impl.ProductServiceImpl;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CategoryResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CustomPageResponse;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.ProductResponseDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController implements CategoryControllerDocs {

    private final CategoryService categoryService;
    private final ProductServiceImpl productService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        CategoryResponseDto response = categoryService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CustomPageResponse<CategoryResponseDto>> getAllCategories(Pageable pageable) {
        Page<CategoryResponseDto> page = categoryService.findAll(pageable);
        return ResponseEntity.ok(new CustomPageResponse<>(page));
    }

    @GetMapping("/search")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@RequestParam String name) {
        CategoryResponseDto response = categoryService.findByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<CustomPageResponse<ProductResponseDto>> getProductsByCategory(
            @PathVariable Long categoryId, Pageable pageable) {
        Page<ProductResponseDto> page = productService.findAllByCategory(categoryId, pageable);
        return ResponseEntity.ok(new CustomPageResponse<>(page));
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductResponseDto> addProductToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        ProductResponseDto updatedProduct = productService.addProductToCategory(categoryId, productId);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto createDto) {
        CategoryResponseDto newCategory = categoryService.create(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> alterCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto updateDto) {
        CategoryResponseDto existingCategory = categoryService.update(id, updateDto);
        return ResponseEntity.ok(existingCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}