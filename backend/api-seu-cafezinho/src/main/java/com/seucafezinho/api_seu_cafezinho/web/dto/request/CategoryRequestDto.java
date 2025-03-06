package com.seucafezinho.api_seu_cafezinho.web.dto.request;

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
public class CategoryRequestDto {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 1, max = 100, message = "The name needs up to 100 characters")
    private String name;
}
