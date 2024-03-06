package com.example.backend.common.exceptions;

import com.example.backend.common.enums.ExceptionStatus;

public class BadRequestException extends RuntimeException {

  private final ExceptionStatus exceptionStatus;

  public BadRequestException(ExceptionStatus exceptionStatus) {
    this.exceptionStatus = exceptionStatus;
  }
}
