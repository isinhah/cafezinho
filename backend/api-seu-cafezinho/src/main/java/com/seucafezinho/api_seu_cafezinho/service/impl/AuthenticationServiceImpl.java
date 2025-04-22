package com.seucafezinho.api_seu_cafezinho.service.impl;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.security.JwtTokenService;
import com.seucafezinho.api_seu_cafezinho.service.AuthenticationService;
import com.seucafezinho.api_seu_cafezinho.service.UserService;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.LoginRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.TokenResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.UserResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.exception.InvalidPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtTokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public TokenResponseDto register(UserRequestDto dto) {
        UserResponseDto createdUserDto = userService.create(dto);
        User createdUser = userService.findUserById(createdUserDto.getId());

        String token = tokenService.generateToken(createdUser);
        Instant expiresAt = tokenService.generateExpirationDate();

        return new TokenResponseDto(createdUser.getId(), token, expiresAt);
    }

    public TokenResponseDto login(LoginRequestDto dto) {
        User user = userService.findByEmail(dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        String token = tokenService.generateToken(user);
        Instant expiresAt = tokenService.generateExpirationDate();

        return new TokenResponseDto(user.getId(), token, expiresAt);
    }
}