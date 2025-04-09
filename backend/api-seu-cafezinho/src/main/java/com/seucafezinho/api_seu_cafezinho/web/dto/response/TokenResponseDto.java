package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TokenResponseDto {
    private UUID userId;
    private String token;
    private Instant expiresAt;
}