package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar um usuário")
public class UserResponseDto {

    @Schema(description = "Unique identifier of the user.", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "User's full name.", example = "John Doe")
    private String name;

    @Schema(description = "User's email address.", example = "user@example.com")
    private String email;

    @Schema(description = "User's phone number in E.164 format.", example = "+15551234567")
    private String phone;

    @Schema(description = "User role, either 'ADMIN' or 'USER'.", example = "USER")
    private String role;
}