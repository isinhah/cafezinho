package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar um produto")
public class ProductResponseDto {

    @Schema(description = "Unique identifier of the product.", example = "1")
    private Long id;

    @Schema(description = "Unique identifier of the category to which the product belongs.", example = "10")
    private Long categoryId;

    @Schema(description = "Name of the product.", example = "Espresso Coffee")
    private String name;

    @Schema(description = "URL of the product image.", example = "https://example.com/images/espresso.jpg")
    private String imageUrl;

    @Schema(description = "Description of the product.", example = "A strong and aromatic espresso coffee")
    private String description;

    @Schema(description = "Price of the product.", example = "4.50")
    private BigDecimal price;
}