package com.finance.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public ResponseEntity<ErrorResponseDto> handleException(
      final Throwable e
  ) {
    LOGGER.warn("An error occurred", e);

    return new ResponseEntity<>(toErrorResponseDto(100000, e), HttpStatus.INTERNAL_SERVER_ERROR);
  }


  @ExceptionHandler(NotFoundException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponseDto> handleException(
      final NotFoundException e
  ) {
    LOGGER.info("An error occurred", e);

    return new ResponseEntity<>(toErrorResponseDto(100001, e), HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(BadRequestException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponseDto> handleException(
      final BadRequestException e
  ) {
    LOGGER.info("An error occurred", e);

    return new ResponseEntity<>(toErrorResponseDto(100002, e), HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(ConflictException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponseDto> handleException(
      final ConflictException e
  ) {
    LOGGER.info("An error occurred", e);

    return new ResponseEntity<>(toErrorResponseDto(100002, e), HttpStatus.CONFLICT);
  }

  private ErrorResponseDto toErrorResponseDto(final int code, final Throwable e) {
    return new ErrorResponseDto(e.getMessage(), code);
  }

}
