package com.finance.exception;

public class BadRequestException extends RuntimeException {
  private final String message;

  public BadRequestException(final String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
