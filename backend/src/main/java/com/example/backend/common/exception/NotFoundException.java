package com.example.backend.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

  private ResponseStatus responseStatus;

  public NotFoundException(ResponseStatus responseStatus) {
    this.responseStatus = responseStatus;
  }
}
