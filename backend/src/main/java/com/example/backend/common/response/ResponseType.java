package com.example.backend.common.response;

public enum ResponseType {
  SUCCESS(200, "성공"),
  FAILURE(400, "실패");

  private int code;
  private String message;

  ResponseType(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
}
