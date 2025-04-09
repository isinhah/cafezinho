package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.security.JwtTokenService;
import com.seucafezinho.api_seu_cafezinho.service.UserService;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.LoginRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.TokenResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtTokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> register(@RequestBody @Valid UserRequestDto dto) {
        UserResponseDto createdUserDto = userService.create(dto);
        User createdUser = userService.findUserById(createdUserDto.getId());

        String token = tokenService.generateToken(createdUser);
        Instant expiresAt = tokenService.generateExpirationDate();

        return ResponseEntity.ok(new TokenResponseDto(createdUser.getId(), token, expiresAt));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        User user = userService.findByEmail(dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenService.generateToken(user);
        Instant expiresAt = tokenService.generateExpirationDate();

        return ResponseEntity.ok(new TokenResponseDto(user.getId(), token, expiresAt));
    }
}