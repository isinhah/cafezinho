package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import jakarta.validation.constraints.Email;
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
            regexp = "^\\+[1-9]\\d{9,14}$",
            message = "Phone number must be in E.164 format (e.g., +5511922223333)"
    )
    private String phone;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-_]+@[a-z0-9.-]+\\.[a-z]{2,}$"
    )
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    private String password;

    private String role;
}