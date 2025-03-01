package com.seucafezinho.api_seu_cafezinho.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String statusMessage;
    private String errorMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> validationErrors;

    public ErrorMessage(
            HttpServletRequest request,
            HttpStatus status,
            String errorMessage) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.errorMessage = errorMessage;
    }

    public ErrorMessage(
            HttpServletRequest request,
            HttpStatus status,
            String errorMessage,
            BindingResult validationResult) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.errorMessage = errorMessage;
        addErrors(validationResult);
    }

    private void addErrors(BindingResult validationResult) {
        this.validationErrors = new HashMap<>();

        for (FieldError fieldError : validationResult.getFieldErrors()) {
            this.validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}