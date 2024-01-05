package com.example.backend.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Response<T> {
  private int code;
  private String message;
  private T data;

  @Builder
  public Response(ResponseType responseType, T data) {
    this.code = responseType.getCode();
    this.message = responseType.getMessage();
    this.data = data;
  }

  public static <T> Response<T> success() {
    return Response.<T>builder()
        .responseType(ResponseType.SUCCESS)
        .build();
  }

  public static <T> Response<T> success(T data) {
    return Response.<T>builder()
        .responseType(ResponseType.SUCCESS)
        .data(data)
        .build();
  }

  public static Response failure() {
    return Response.builder()
        .responseType(ResponseType.FAILURE)
        .build();
  }

  public static <T> Response<T> failure(T data) {
    return Response.<T>builder()
        .responseType(ResponseType.FAILURE)
        .data(data)
        .build();
  }
}