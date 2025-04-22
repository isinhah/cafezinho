package com.seucafezinho.api_seu_cafezinho.web.exception;

public class InvalidOrderException extends RuntimeException {
  public InvalidOrderException(String message) {
    super(message);
  }
}
