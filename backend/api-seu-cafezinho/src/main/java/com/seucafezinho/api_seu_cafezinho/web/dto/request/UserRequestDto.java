package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO para criação de usuário")
public class UserRequestDto {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
    @Schema(description = "User's full name.", example = "John Doe")
    private String name;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(
            regexp = "^\\+[1-9]\\d{9,14}$",
            message = "Phone number must be in E.164 format (e.g., +5511922223333)"
    )
    @Schema(description = "User's phone number in E.164 format.", example = "+15551234567")
    private String phone;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-_]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @Schema(description = "User's email address.", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    @Schema(description = "User's password. Must be exactly 6 characters long.", example = "123456")
    private String password;

    @Schema(description = "User role, either 'ADMIN' or 'USER'. Defaults to 'USER' if not specified.", example = "USER")
    private String role;
}