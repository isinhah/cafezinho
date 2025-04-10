package com.seucafezinho.api_seu_cafezinho.util;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.LoginRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.TokenResponseDto;

import java.time.Instant;
import java.util.UUID;

import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_EMAIL;
import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_ID;
import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_PASSWORD;

public class AuthenticationConstants {

    public static final UUID AUTH_USER_ID = USER_ID;
    public static final String AUTH_EMAIL = USER_EMAIL;
    public static final String AUTH_PASSWORD = USER_PASSWORD;

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";
    public static final Instant EXPIRATION_TIME = Instant.parse("2025-04-10T15:30:00Z");

    public static final LoginRequestDto LOGIN_REQUEST_DTO =
            new LoginRequestDto(AUTH_EMAIL, AUTH_PASSWORD);

    public static final TokenResponseDto TOKEN_RESPONSE_DTO =
            new TokenResponseDto(AUTH_USER_ID, TOKEN, EXPIRATION_TIME);
}