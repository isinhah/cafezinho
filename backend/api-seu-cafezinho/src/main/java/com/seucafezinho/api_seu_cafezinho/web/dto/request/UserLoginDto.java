package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import jakarta.validation.constraints.Email;
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
public class UserLoginDto {

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-_]+@[a-z0-9.-]+\\.[a-z]{2,}$"
    )
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    private String password;
}