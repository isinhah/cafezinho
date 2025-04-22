package com.seucafezinho.api_seu_cafezinho.web.exception;

public class UniqueViolationException extends RuntimeException {
    public UniqueViolationException(String message) {
        super(message);
    }
}