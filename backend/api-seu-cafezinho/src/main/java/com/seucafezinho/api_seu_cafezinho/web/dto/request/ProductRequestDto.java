package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de um produto")
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name can have up to 100 characters")
    @Schema(description = "Name of the product.", example = "Espresso Coffee")
    private String name;

    @NotBlank(message = "Image URL is required")
    @Size(max = 255, message = "Image URL can have up to 255 characters")
    @Schema(description = "URL of the product image.", example = "https://example.com/images/espresso.jpg")
    private String imageUrl;

    @NotBlank(message = "Description is required")
    @Size(max = 400, message = "Description can have up to 400 characters")
    @Schema(description = "Description of the product.", example = "A strong and aromatic espresso coffee")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount with up to 10 digits and 2 decimal places")
    @Schema(description = "Price of the product.", example = "4.50")
    private BigDecimal price;
}