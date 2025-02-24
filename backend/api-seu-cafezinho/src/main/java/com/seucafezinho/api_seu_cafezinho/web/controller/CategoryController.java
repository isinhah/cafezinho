package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.service.CategoryService;
import com.seucafezinho.api_seu_cafezinho.web.dto.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.CategoryResponseDto;
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
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id) {
        CategoryResponseDto response = categoryService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public Page<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<CategoryResponseDto> getByName(@RequestParam String name) {
        CategoryResponseDto response = categoryService.findByName(name);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@Valid @RequestBody CategoryRequestDto createDto) {
        CategoryResponseDto newCategoryResponse = categoryService.save(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategoryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> alter(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto updateDto) {
        CategoryResponseDto existingCategory = categoryService.update(id, updateDto);
        return ResponseEntity.ok(existingCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}