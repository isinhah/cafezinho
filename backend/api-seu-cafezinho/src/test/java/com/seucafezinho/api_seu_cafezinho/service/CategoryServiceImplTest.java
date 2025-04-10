package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.repository.CategoryRepository;
import com.seucafezinho.api_seu_cafezinho.service.impl.CategoryServiceImpl;
import com.seucafezinho.api_seu_cafezinho.util.CategoryConstants;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CategoryResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.exception.UniqueViolationException;
import com.seucafezinho.api_seu_cafezinho.web.mapper.CategoryMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findById_ShouldReturnCategoryResponseDto_WhenSuccessful() {
        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID))
                .thenReturn(Optional.of(CategoryConstants.CATEGORY));

        CategoryResponseDto response = categoryService.findById(CategoryConstants.CATEGORY_ID);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(CategoryConstants.CATEGORY_RESPONSE_DTO);

        verify(categoryRepository).findById(CategoryConstants.CATEGORY_ID);
    }

    @Test
    void findById_ShouldThrowResourceNotFoundException_WhenCategoryDoesNotExist() {
        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                categoryService.findById(CategoryConstants.CATEGORY_ID)
        );

        verify(categoryRepository).findById(CategoryConstants.CATEGORY_ID);
    }

    @Test
    void findByName_ShouldReturnCategoryResponseDto_WhenSuccessful() {
        when(categoryRepository.findByNameIgnoreCase(CategoryConstants.CATEGORY_NAME))
                .thenReturn(Optional.of(CategoryConstants.CATEGORY));

        CategoryResponseDto response = categoryService.findByName(CategoryConstants.CATEGORY_NAME);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(CategoryConstants.CATEGORY_RESPONSE_DTO);

        verify(categoryRepository).findByNameIgnoreCase(CategoryConstants.CATEGORY_NAME);
    }

    @Test
    void findByName_ShouldThrowResourceNotFoundException_WhenCategoryDoesNotExist() {
        when(categoryRepository.findByNameIgnoreCase(CategoryConstants.CATEGORY_NAME))
                .thenReturn(Optional.empty());

        assertThrows(UniqueViolationException.class, () ->
                categoryService.findByName(CategoryConstants.CATEGORY_NAME)
        );

        verify(categoryRepository).findByNameIgnoreCase(CategoryConstants.CATEGORY_NAME);
    }

    @Test
    void findAll_ShouldReturnPageOfCategoryResponseDto_WhenSuccessful() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(List.of(CategoryConstants.CATEGORY));

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        Page<CategoryResponseDto> result = categoryService.findAll(pageable);

        assertThat(result.getContent())
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(CategoryConstants.CATEGORY_RESPONSE_DTO);

        verify(categoryRepository).findAll(pageable);
    }

    @Test
    void create_ShouldSaveAndReturnCategoryResponseDto_WhenSuccessful() {
        CategoryRequestDto requestDto = CategoryConstants.CATEGORY_REQUEST_DTO;
        Category categoryToSave = CategoryMapper.INSTANCE.toCategory(requestDto);

        when(categoryRepository.existsByNameIgnoreCase(requestDto.getName())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(CategoryConstants.CATEGORY);

        CategoryResponseDto response = categoryService.create(requestDto);

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(CategoryConstants.CATEGORY_RESPONSE_DTO);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void create_ShouldThrowDuplicateResourceException_WhenCategoryNameAlreadyExists() {
        when(categoryRepository.existsByNameIgnoreCase(CategoryConstants.CATEGORY_NAME))
                .thenReturn(true);

        assertThrows(UniqueViolationException.class, () ->
                categoryService.create(CategoryConstants.CATEGORY_REQUEST_DTO)
        );

        verify(categoryRepository).existsByNameIgnoreCase(CategoryConstants.CATEGORY_NAME);
    }

    @Test
    void update_ShouldUpdateAndReturnCategoryResponseDto_WhenSuccessful() {
        CategoryRequestDto updateDto = CategoryConstants.CATEGORY_REQUEST_DTO;

        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID))
                .thenReturn(Optional.of(CategoryConstants.CATEGORY));
        when(categoryRepository.existsByNameIgnoreCaseAndIdNot(updateDto.getName(), CategoryConstants.CATEGORY_ID))
                .thenReturn(false);
        when(categoryRepository.save(any(Category.class)))
                .thenReturn(CategoryConstants.CATEGORY);

        CategoryResponseDto response = categoryService.update(CategoryConstants.CATEGORY_ID, updateDto);

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(CategoryConstants.CATEGORY_RESPONSE_DTO);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void update_ShouldThrowDuplicateResourceException_WhenCategoryNameAlreadyExistsForOtherId() {
        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID))
                .thenReturn(Optional.of(CategoryConstants.CATEGORY));
        when(categoryRepository.existsByNameIgnoreCaseAndIdNot(
                CategoryConstants.CATEGORY_NAME, CategoryConstants.CATEGORY_ID))
                .thenReturn(true);

        assertThrows(UniqueViolationException.class, () ->
                categoryService.update(CategoryConstants.CATEGORY_ID, CategoryConstants.CATEGORY_REQUEST_DTO)
        );

        verify(categoryRepository).existsByNameIgnoreCaseAndIdNot(CategoryConstants.CATEGORY_NAME, CategoryConstants.CATEGORY_ID);
    }

    @Test
    void update_ShouldThrowResourceNotFoundException_WhenCategoryDoesNotExist() {
        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                categoryService.update(CategoryConstants.CATEGORY_ID, CategoryConstants.CATEGORY_REQUEST_DTO)
        );

        verify(categoryRepository).findById(CategoryConstants.CATEGORY_ID);
    }

    @Test
    void delete_ShouldRemoveCategory_WhenSuccessful() {
        Category category = CategoryConstants.CATEGORY;
        category.getProducts().forEach(product -> product.setCategory(category));

        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID))
                .thenReturn(Optional.of(category));

        categoryService.delete(CategoryConstants.CATEGORY_ID);

        assertThat(category.getProducts())
                .allMatch(product -> product.getCategory() == null);

        verify(categoryRepository).delete(category);
    }

    @Test
    void delete_ShouldThrowResourceNotFoundException_WhenCategoryDoesNotExist() {
        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                categoryService.delete(CategoryConstants.CATEGORY_ID)
        );

        verify(categoryRepository).findById(CategoryConstants.CATEGORY_ID);
    }
}