package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.entity.Product;
import com.seucafezinho.api_seu_cafezinho.repository.CategoryRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CategoryResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.exception.UniqueViolationException;
import com.seucafezinho.api_seu_cafezinho.web.mapper.CategoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CategoryResponseDto findById(Long id) {
        Category category = findCategoryById(id);
        return CategoryMapper.INSTANCE.toDto(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(CategoryMapper.INSTANCE::toDto);
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto findByName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name).orElseThrow(
                () -> new UniqueViolationException(String.format("Category with name: '%s' not found", name))
        );
        return CategoryMapper.INSTANCE.toDto(category);
    }

    @Transactional
    public CategoryResponseDto create(CategoryRequestDto createDto) {
        if (categoryRepository.existsByNameIgnoreCase(createDto.getName())) {
            throw new UniqueViolationException(String.format("The name: '%s' already exists", createDto.getName()));
        }

        Category categoryToSave = CategoryMapper.INSTANCE.toCategory(createDto);
        Category savedCategory = categoryRepository.save(categoryToSave);
        return CategoryMapper.INSTANCE.toDto(savedCategory);
    }

    @Transactional
    public CategoryResponseDto update(Long id, CategoryRequestDto updateDto) {
        Category existingCategory = findCategoryById(id);

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(updateDto.getName(), id)) {
            throw new UniqueViolationException(String.format("The name: '%s' already exists", updateDto.getName()));
        }

        CategoryMapper.INSTANCE.updateCategoryFromDto(updateDto, existingCategory);

        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.INSTANCE.toDto(updatedCategory);
    }

    @Transactional
    public void delete(Long id) {
        Category category = findCategoryById(id);

        for (Product product : category.getProducts()) {
            product.setCategory(null);
        }

        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Category with id: '%s' not found", id)));
    }
}