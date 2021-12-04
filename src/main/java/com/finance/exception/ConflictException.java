package com.finance.exception;

public class ConflictException extends RuntimeException {
  private final String message;

  public ConflictException(final String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
