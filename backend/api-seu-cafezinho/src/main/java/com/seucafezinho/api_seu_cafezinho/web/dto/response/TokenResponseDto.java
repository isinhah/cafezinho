package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Schema(description = "DTO para representar a resposta da autenticação")
public class TokenResponseDto {

    @Schema(description = "Unique identifier of the authenticated user.", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userId;

    @Schema(description = "JWT token generated after authentication.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Expiration timestamp of the token in ISO-8601 format.", example = "2025-04-10T15:30:00Z")
    private Instant expiresAt;
}