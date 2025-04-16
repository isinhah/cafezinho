package com.seucafezinho.api_seu_cafezinho.security;

import com.seucafezinho.api_seu_cafezinho.web.exception.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityValidator {

    public static UUID getCurrentUserId() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        return UUID.fromString(id);
    }

    public static void validateUserAccess(UUID resourceOwnerId) {
        UUID currentUserId = getCurrentUserId();
        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!resourceOwnerId.equals(currentUserId) && !isAdmin) {
            throw new AccessDeniedException("You are not allowed to access this feature.");
        }
    }
}