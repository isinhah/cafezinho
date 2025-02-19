package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.repository.CategoryRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.CategoryResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        Category category = categoryRepository.findByName(name).orElseThrow(
                () -> new RuntimeException(String.format("Category with name: '%s' not found", name))
        );
        return CategoryMapper.INSTANCE.toDto(category);
    }

    @Transactional
    public CategoryResponseDto save(CategoryRequestDto createDto) {
        Category categoryToSave = CategoryMapper.INSTANCE.toCategory(createDto);

        try {
            Category savedCategory = categoryRepository.save(categoryToSave);
            return CategoryMapper.INSTANCE.toDto(savedCategory);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException(String.format("The name: '%s' already exists", createDto.getName()));
        }
    }

    @Transactional
    public CategoryResponseDto update(Long id, CategoryRequestDto updateDto) {
        Category existingCategory = findCategoryById(id);

        if (categoryRepository.existsByName(updateDto.getName())) {
            throw new RuntimeException(String.format("The name: '%s' already exists", updateDto.getName()));
        }

        existingCategory.setName(updateDto.getName());

        try {
            Category updatedCategory = categoryRepository.save(existingCategory);
            return CategoryMapper.INSTANCE.toDto(updatedCategory);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException(String.format("The name: '%s' already exists", updateDto.getName()));
        }
    }

    @Transactional
    public void delete(Long id) {
        Category category = findCategoryById(id);

        if (!category.isActive()) {
            throw new RuntimeException(String.format("Category with id: %s is already inactive", id));
        }

        category.setActive(false);
        categoryRepository.save(category);
    }

    @Transactional
    public CategoryResponseDto reactivate(Long id) {
        Category category = findCategoryById(id);

        if (category.isActive()) {
            throw new RuntimeException(String.format("Category with id: '%s' is already active", id));
        }

        category.setActive(true);
        Category reactivatedCategory = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toDto(reactivatedCategory);
    }

    @Transactional(readOnly = true)
    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Category with id: '%s' not found", id)));
    }
}