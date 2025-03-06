package com.seucafezinho.api_seu_cafezinho.web.dto.request;

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
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name can have up to 100 characters")
    private String name;

    @NotBlank(message = "Image URL is required")
    @Size(max = 255, message = "Image URL can have up to 255 characters")
    private String imageUrl;

    @NotBlank(message = "Description is required")
    @Size(max = 400, message = "Description can have up to 400 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount with up to 10 digits and 2 decimal places")
    private BigDecimal price;
}