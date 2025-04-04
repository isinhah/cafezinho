package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação ou atualização de uma categoria")
public class CategoryRequestDto {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 1, max = 100, message = "The name needs up to 100 characters")
    @Schema(description = "Name of the category.", example = "Drinks")
    private String name;
}