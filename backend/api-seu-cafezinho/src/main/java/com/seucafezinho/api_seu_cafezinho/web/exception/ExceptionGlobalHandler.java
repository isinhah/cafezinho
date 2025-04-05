package com.seucafezinho.api_seu_cafezinho.web.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException ex,
                                                                HttpServletRequest request) {
        log.error("------ Api Error ------", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()));
    }

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ErrorMessage> uniqueViolationException(UniqueViolationException ex,
                                                                 HttpServletRequest request) {
        log.error("------ Api Error ------", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.BAD_REQUEST,
                        "Unique constraint violation: " + ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException ex,
                                                                 HttpServletRequest request) {
        log.error("------ Api Error ------", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.BAD_REQUEST,
                        "Invalid argument: " + ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> businessException(RuntimeException ex,
                                                                HttpServletRequest request) {
        log.error("------ Api Error ------", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        log.error("------ Api Error ------", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Invalid field(s)",
                        result));
    }
}