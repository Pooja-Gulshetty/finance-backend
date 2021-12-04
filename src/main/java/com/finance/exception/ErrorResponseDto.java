package com.finance.exception;

public class ErrorResponseDto {
  private final String message;
  private final int code;

  public ErrorResponseDto(final String message, final int code) {
    this.message = message;
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public int getCode() {
    return code;
  }
}
