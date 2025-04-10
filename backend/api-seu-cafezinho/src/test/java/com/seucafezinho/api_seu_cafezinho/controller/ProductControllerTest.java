package com.seucafezinho.api_seu_cafezinho.controller;

import com.seucafezinho.api_seu_cafezinho.service.ProductService;
import com.seucafezinho.api_seu_cafezinho.util.ProductConstants;
import com.seucafezinho.api_seu_cafezinho.web.controller.ProductController;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.ProductRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.ProductResponseDto;
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
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void testGetProductById_WhenSuccessful() {
        when(productService.findById(ProductConstants.PRODUCT_ID))
                .thenReturn(ProductConstants.PRODUCT_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            productController.getProductById(ProductConstants.PRODUCT_ID);
        });

        verify(productService, times(1)).findById(ProductConstants.PRODUCT_ID);
    }

    @Test
    void testGetAllProducts_WhenSuccessful() {
        List<ProductResponseDto> products = List.of(ProductConstants.PRODUCT_RESPONSE_DTO);
        Page<ProductResponseDto> page = new PageImpl<>(products);
        Pageable pageable = PageRequest.of(0, 10);

        when(productService.findAll(pageable)).thenReturn(page);

        assertDoesNotThrow(() -> {
            productController.getAllProducts(pageable);
        });

        verify(productService, times(1)).findAll(pageable);
    }

    @Test
    void testCreateProduct_WhenSuccessful() {
        ProductRequestDto requestDto = ProductConstants.PRODUCT_REQUEST_DTO;

        when(productService.create(requestDto)).thenReturn(ProductConstants.PRODUCT_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            productController.createProduct(requestDto);
        });

        verify(productService, times(1)).create(requestDto);
    }

    @Test
    void testAlterProduct_WhenSuccessful() {
        ProductRequestDto requestDto = ProductConstants.PRODUCT_REQUEST_DTO;

        when(productService.update(ProductConstants.PRODUCT_ID, requestDto))
                .thenReturn(ProductConstants.PRODUCT_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            productController.alterProduct(ProductConstants.PRODUCT_ID, requestDto);
        });

        verify(productService, times(1)).update(ProductConstants.PRODUCT_ID, requestDto);
    }

    @Test
    void testDeleteProduct_WhenSuccessful() {
        doNothing().when(productService).delete(ProductConstants.PRODUCT_ID);

        assertDoesNotThrow(() -> {
            productController.deleteProduct(ProductConstants.PRODUCT_ID);
        });

        verify(productService, times(1)).delete(ProductConstants.PRODUCT_ID);
    }
}
