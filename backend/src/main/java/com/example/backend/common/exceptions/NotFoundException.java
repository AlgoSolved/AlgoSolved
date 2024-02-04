package com.example.backend.common.exceptions;

import com.example.backend.common.enums.ExceptionStatus;

public class NotFoundException extends RuntimeException {

  private ExceptionStatus exceptionStatus;

  public NotFoundException(ExceptionStatus exceptionStatus) {
    this.exceptionStatus = exceptionStatus;
  }
}
