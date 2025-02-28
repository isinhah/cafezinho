package com.seucafezinho.api_seu_cafezinho.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;
    private Long categoryId;
    private String name;
    private String imageUrl;
    private String description;
    private BigDecimal price;
}