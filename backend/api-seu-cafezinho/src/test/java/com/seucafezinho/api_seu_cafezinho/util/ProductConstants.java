package com.seucafezinho.api_seu_cafezinho.util;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.entity.Product;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.ProductRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.ProductResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductConstants {

    public static final Long PRODUCT_ID = 1L;
    public static final String PRODUCT_NAME = "Espresso Coffee";
    public static final String PRODUCT_IMAGE_URL = "https://example.com/images/espresso.jpg";
    public static final String PRODUCT_DESCRIPTION = "A strong and aromatic espresso coffee";
    public static final BigDecimal PRODUCT_PRICE = new BigDecimal("4.50");
    public static final LocalDateTime PRODUCT_CREATED_DATE = LocalDateTime.of(2024, 1, 1, 10, 0, 0);
    public static final LocalDateTime PRODUCT_UPDATED_DATE = LocalDateTime.of(2024, 1, 2, 12, 0, 0);

    public static final Category PRODUCT_CATEGORY = CategoryConstants.CATEGORY;

    public static final Product PRODUCT = new Product(
            PRODUCT_ID,
            PRODUCT_NAME,
            PRODUCT_IMAGE_URL,
            PRODUCT_DESCRIPTION,
            PRODUCT_PRICE,
            PRODUCT_CREATED_DATE,
            PRODUCT_UPDATED_DATE,
            PRODUCT_CATEGORY,
            null
    );

    public static final ProductRequestDto PRODUCT_REQUEST_DTO = new ProductRequestDto(
            PRODUCT_NAME,
            PRODUCT_IMAGE_URL,
            PRODUCT_DESCRIPTION,
            PRODUCT_PRICE
    );

    public static final ProductResponseDto PRODUCT_RESPONSE_DTO = new ProductResponseDto(
            PRODUCT_ID,
            PRODUCT_CATEGORY.getId(),
            PRODUCT_NAME,
            PRODUCT_IMAGE_URL,
            PRODUCT_DESCRIPTION,
            PRODUCT_PRICE
    );
}