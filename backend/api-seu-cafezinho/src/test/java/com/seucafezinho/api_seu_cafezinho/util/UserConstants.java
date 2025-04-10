package com.seucafezinho.api_seu_cafezinho.util;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.UserResponseDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

public class UserConstants {

    public static final UUID USER_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
    public static final String USER_NAME = "John Doe";
    public static final String USER_EMAIL = "user@example.com";
    public static final String USER_PHONE = "+15551234567";
    public static final String USER_PASSWORD = "123456";
    public static final String USER_ROLE = "USER";

    public static final UserRequestDto USER_REQUEST_DTO = new UserRequestDto(
            USER_NAME,
            USER_PHONE,
            USER_EMAIL,
            USER_PASSWORD,
            USER_ROLE
    );

    public static final UserResponseDto USER_RESPONSE_DTO = new UserResponseDto(
            USER_ID,
            USER_NAME,
            USER_EMAIL,
            USER_PHONE,
            USER_ROLE
    );

    public static final User USER = new User(
            USER_ID,
            USER_NAME,
            USER_EMAIL,
            USER_PHONE,
            USER_PASSWORD,
            User.Role.ROLE_USER,
            LocalDateTime.now(),
            LocalDateTime.now(),
            new HashSet<>()
    );
}
