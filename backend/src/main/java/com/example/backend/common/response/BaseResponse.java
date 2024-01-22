package com.example.backend.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {

  private String code;
  private String message;
  private T data;

  @Builder
  public BaseResponse(final String code, final String message, T data) {
    this.code = code;
    this.message = message;
    this.data = null;
  }

  public static <T> BaseResponse<T> success(final String code, final String message) {
    return success(code, message, null);
  }

  public static <T> BaseResponse<T> success(final String code, final String message, final T data) {
    return new BaseResponse<>(code, message, data);
  }


}
