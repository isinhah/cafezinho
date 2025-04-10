package com.seucafezinho.api_seu_cafezinho.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.mockito.Mockito.*;

public class SecurityContextTestUtil {

    public static void mockAuthenticatedUser(String uuidAsString) {
        Authentication auth = new UsernamePasswordAuthenticationToken(uuidAsString, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static void clear() {
        SecurityContextHolder.clearContext();
    }
}