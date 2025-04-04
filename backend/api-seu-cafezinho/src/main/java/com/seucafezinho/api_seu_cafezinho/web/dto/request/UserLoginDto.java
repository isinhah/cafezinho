package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO para login do usuário cadastrado no sistema")
public class UserLoginDto {

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-_]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @Schema(description = "User's email address for authentication.", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    @Schema(description = "User's password for authentication. Must be exactly 6 characters long.", example = "123456")
    private String password;
}