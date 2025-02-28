package com.seucafezinho.api_seu_cafezinho.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 10 and 50 characters long")
    private String name;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(
            regexp = "^[0-9]{10,16}$",
            message = "Phone number must contain only numbers and be between 10 and 16 digits"
    )
    private String phone;

    private String role;
}