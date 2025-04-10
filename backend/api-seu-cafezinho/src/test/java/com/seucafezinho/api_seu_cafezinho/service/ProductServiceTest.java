package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Product;
import com.seucafezinho.api_seu_cafezinho.repository.CategoryRepository;
import com.seucafezinho.api_seu_cafezinho.repository.ProductRepository;
import com.seucafezinho.api_seu_cafezinho.service.impl.ProductServiceImpl;
import com.seucafezinho.api_seu_cafezinho.util.CategoryConstants;
import com.seucafezinho.api_seu_cafezinho.util.ProductConstants;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.ProductResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.exception.UniqueViolationException;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findById_ShouldReturnProductResponseDto_WhenSuccessful() {
        when(productRepository.findById(ProductConstants.PRODUCT_ID))
                .thenReturn(Optional.of(ProductConstants.PRODUCT));

        ProductResponseDto response = productService.findById(ProductConstants.PRODUCT_ID);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(ProductConstants.PRODUCT_RESPONSE_DTO);
        verify(productRepository).findById(ProductConstants.PRODUCT_ID);
    }

    @Test
    void findById_ShouldThrowEntityNotFoundException_WhenProductDoesNotExist() {
        when(productRepository.findById(ProductConstants.PRODUCT_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                productService.findById(ProductConstants.PRODUCT_ID));

        assertEquals("Product with id: '1' not found", exception.getMessage());
    }

    @Test
    void findAll_ShouldReturnPageOfProductResponseDto_WhenSuccessful() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(ProductConstants.PRODUCT));

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<ProductResponseDto> result = productService.findAll(pageable);

        assertThat(result.getContent())
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(ProductConstants.PRODUCT_RESPONSE_DTO);
        verify(productRepository).findAll(pageable);
    }

    @Test
    void findAllByCategory_ShouldReturnPageOfProductResponseDto_WhenSuccessful() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(ProductConstants.PRODUCT));

        when(categoryRepository.findById(ProductConstants.PRODUCT_CATEGORY.getId()))
                .thenReturn(Optional.of(ProductConstants.PRODUCT_CATEGORY));
        when(productRepository.findAllByCategory(ProductConstants.PRODUCT_CATEGORY, pageable))
                .thenReturn(productPage);

        Page<ProductResponseDto> result = productService.findAllByCategory(
                ProductConstants.PRODUCT_CATEGORY.getId(), pageable
        );

        assertThat(result.getContent())
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(ProductConstants.PRODUCT_RESPONSE_DTO);
        verify(categoryRepository).findById(ProductConstants.PRODUCT_CATEGORY.getId());
        verify(productRepository).findAllByCategory(ProductConstants.PRODUCT_CATEGORY, pageable);
    }

    @Test
    void findAllByCategory_ShouldThrowEntityNotFoundException_WhenCategoryDoesNotExist() {
        when(categoryRepository.findById(ProductConstants.PRODUCT_CATEGORY.getId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                productService.findAllByCategory(ProductConstants.PRODUCT_CATEGORY.getId(), PageRequest.of(0, 10)));

        assertEquals("Category with id: '1' not found", exception.getMessage());
    }

    @Test
    void testAddProductToCategory_WhenSuccessful() {
        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID))
                .thenReturn(Optional.of(CategoryConstants.CATEGORY));

        when(productRepository.findById(ProductConstants.PRODUCT_ID))
                .thenReturn(Optional.of(ProductConstants.PRODUCT));

        ProductResponseDto result = productService.addProductToCategory(CategoryConstants.CATEGORY_ID, ProductConstants.PRODUCT_ID);

        assertNotNull(result);
        assertEquals(ProductConstants.PRODUCT_NAME, result.getName());
        verify(categoryRepository, times(1)).save(CategoryConstants.CATEGORY);
    }

    @Test
    void addProductToCategory_ShouldThrowEntityNotFoundException_WhenCategoryDoesNotExist() {
        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                productService.addProductToCategory(CategoryConstants.CATEGORY_ID, ProductConstants.PRODUCT_ID));

        assertEquals("Category with id: '1' not found", exception.getMessage());
    }

    @Test
    void addProductToCategory_ShouldThrowEntityNotFoundException_WhenProductDoesNotExist() {
        when(categoryRepository.findById(CategoryConstants.CATEGORY_ID)).thenReturn(Optional.of(CategoryConstants.CATEGORY));
        when(productRepository.findById(ProductConstants.PRODUCT_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                productService.addProductToCategory(CategoryConstants.CATEGORY_ID, ProductConstants.PRODUCT_ID));

        assertEquals("Product with id: '1' not found", exception.getMessage());
    }

    @Test
    void create_ShouldSaveAndReturnProductResponseDto_WhenSuccessful() {
        when(productRepository.save(any(Product.class)))
                .thenReturn(ProductConstants.PRODUCT);

        ProductResponseDto response = productService.create(ProductConstants.PRODUCT_REQUEST_DTO);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(ProductConstants.PRODUCT_RESPONSE_DTO);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void update_ShouldUpdateAndReturnProductResponseDto_WhenSuccessful() {
        when(productRepository.findById(ProductConstants.PRODUCT_ID))
                .thenReturn(Optional.of(ProductConstants.PRODUCT));
        when(productRepository.save(any(Product.class)))
                .thenReturn(ProductConstants.PRODUCT);

        ProductResponseDto response = productService.update(
                ProductConstants.PRODUCT_ID,
                ProductConstants.PRODUCT_REQUEST_DTO
        );

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(ProductConstants.PRODUCT_RESPONSE_DTO);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void update_ShouldThrowUniqueViolationException_WhenNameAlreadyExistsInAnotherProduct() {
        when(productRepository.findById(ProductConstants.PRODUCT_ID))
                .thenReturn(Optional.of(ProductConstants.PRODUCT));

        when(productRepository.existsByNameIgnoreCaseAndIdNot(
                ProductConstants.PRODUCT_REQUEST_DTO.getName(),
                ProductConstants.PRODUCT_ID))
                .thenReturn(true);

        UniqueViolationException exception = assertThrows(UniqueViolationException.class, () ->
                productService.update(ProductConstants.PRODUCT_ID, ProductConstants.PRODUCT_REQUEST_DTO));

        assertEquals("Another product with the name: 'Espresso Coffee' already exists", exception.getMessage());
    }

    @Test
    void delete_ShouldRemoveProduct_WhenSuccessful() {
        when(productRepository.findById(ProductConstants.PRODUCT_ID))
                .thenReturn(Optional.of(ProductConstants.PRODUCT));

        productService.delete(ProductConstants.PRODUCT_ID);

        verify(productRepository).delete(ProductConstants.PRODUCT);
    }

    @Test
    void delete_ShouldThrowEntityNotFoundException_WhenProductDoesNotExist() {
        when(productRepository.findById(ProductConstants.PRODUCT_ID))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                productService.delete(ProductConstants.PRODUCT_ID));

        assertEquals("Product with id: '1' not found", exception.getMessage());
    }
}