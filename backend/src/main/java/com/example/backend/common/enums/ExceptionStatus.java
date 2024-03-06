package com.example.backend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {
  NOT_FOUND("4040", "데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND("4041", "유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  USERNAME_MISMATCH("4042", "입력한 유저네임이 일치하지 않습니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;
}
