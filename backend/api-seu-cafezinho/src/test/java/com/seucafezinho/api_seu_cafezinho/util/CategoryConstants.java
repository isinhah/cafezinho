package com.seucafezinho.api_seu_cafezinho.util;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.entity.Product;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CategoryResponseDto;

import java.util.HashSet;
import java.util.Set;

public class CategoryConstants {

    public static final Long CATEGORY_ID = 1L;
    public static final String CATEGORY_NAME = "Drinks";
    public static final Set<Product> CATEGORY_PRODUCTS = new HashSet<>();

    public static final Category CATEGORY = new Category(CATEGORY_ID, CATEGORY_NAME, CATEGORY_PRODUCTS);

    public static final CategoryRequestDto CATEGORY_REQUEST_DTO = new CategoryRequestDto(CATEGORY_NAME);

    public static final CategoryResponseDto CATEGORY_RESPONSE_DTO = new CategoryResponseDto(CATEGORY_ID, CATEGORY_NAME);
}