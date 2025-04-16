package com.seucafezinho.api_seu_cafezinho.web.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}