package com.seucafezinho.api_seu_cafezinho.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.seucafezinho.api_seu_cafezinho.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class JwtTokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("name", user.getName())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(generateExpirationDate()))
                .sign(getAlgorithm());
    }

    public boolean isTokenValid(String token) {
        try {
            getDecodedJWT(token);
            return true;
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    public UUID getUserIdFromToken(String token) {
        try {
            DecodedJWT decodedJWT = getDecodedJWT(token);
            return UUID.fromString(decodedJWT.getSubject());
        } catch (Exception e) {
            log.error("Failed to extract user ID from token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid token: cannot extract user ID", e);
        }
    }

    public String getUserRoleFromToken(String token) {
        try {
            DecodedJWT decodedJWT = getDecodedJWT(token);
            return decodedJWT.getClaim("role").asString();
        } catch (Exception e) {
            log.error("Failed to extract role from token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid token: cannot extract role", e);
        }
    }

    private DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.require(getAlgorithm()).build().verify(token);
        } catch (Exception e) {
            log.error("Failed to decode token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid token: cannot decode", e);
        }
    }

    public Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusMinutes(30)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}