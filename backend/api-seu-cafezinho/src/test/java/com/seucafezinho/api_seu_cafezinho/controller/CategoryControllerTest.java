package com.seucafezinho.api_seu_cafezinho.controller;

import com.seucafezinho.api_seu_cafezinho.service.CategoryService;
import com.seucafezinho.api_seu_cafezinho.util.CategoryConstants;
import com.seucafezinho.api_seu_cafezinho.web.controller.CategoryController;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CategoryResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void testGetCategoryById_WhenSuccessful() {
        when(categoryService.findById(CategoryConstants.CATEGORY_ID))
                .thenReturn(CategoryConstants.CATEGORY_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            categoryController.getCategoryById(CategoryConstants.CATEGORY_ID);
        });

        verify(categoryService, times(1)).findById(CategoryConstants.CATEGORY_ID);
    }

    @Test
    void testGetAllCategories_WhenSuccessful() {
        List<CategoryResponseDto> categories = List.of(CategoryConstants.CATEGORY_RESPONSE_DTO);
        Page<CategoryResponseDto> page = new PageImpl<>(categories);
        Pageable pageable = PageRequest.of(0, 10);

        when(categoryService.findAll(pageable)).thenReturn(page);

        assertDoesNotThrow(() -> {
            categoryController.getAllCategories(pageable);
        });

        verify(categoryService, times(1)).findAll(pageable);
    }

    @Test
    void testGetCategoryByName_WhenSuccessful() {
        when(categoryService.findByName(CategoryConstants.CATEGORY_NAME))
                .thenReturn(CategoryConstants.CATEGORY_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            categoryController.getCategoryByName(CategoryConstants.CATEGORY_NAME);
        });

        verify(categoryService, times(1)).findByName(CategoryConstants.CATEGORY_NAME);
    }

    @Test
    void testCreateCategory_WhenSuccessful() {
        CategoryRequestDto requestDto = CategoryConstants.CATEGORY_REQUEST_DTO;

        when(categoryService.create(requestDto)).thenReturn(CategoryConstants.CATEGORY_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            categoryController.createCategory(requestDto);
        });

        verify(categoryService, times(1)).create(requestDto);
    }

    @Test
    void testAlterCategory_WhenSuccessful() {
        CategoryRequestDto requestDto = CategoryConstants.CATEGORY_REQUEST_DTO;

        when(categoryService.update(CategoryConstants.CATEGORY_ID, requestDto))
                .thenReturn(CategoryConstants.CATEGORY_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            categoryController.alterCategory(CategoryConstants.CATEGORY_ID, requestDto);
        });

        verify(categoryService, times(1)).update(CategoryConstants.CATEGORY_ID, requestDto);
    }

    @Test
    void testDeleteCategory_WhenSuccessful() {
        doNothing().when(categoryService).delete(CategoryConstants.CATEGORY_ID);

        assertDoesNotThrow(() -> {
            categoryController.deleteCategory(CategoryConstants.CATEGORY_ID);
        });

        verify(categoryService, times(1)).delete(CategoryConstants.CATEGORY_ID);
    }
}