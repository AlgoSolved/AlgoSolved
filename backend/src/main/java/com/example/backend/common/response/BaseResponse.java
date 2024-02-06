package com.example.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private String code;
    private String message;
    private T data;

    public static BaseResponse<Object> success(String code, String message) {
        return BaseResponse.builder().code(code).message(message).data(null).build();
    }

    public static <T> BaseResponse<Object> success(String code, String message, T data) {
        return BaseResponse.builder().code(code).message(message).data(data).build();
    }
}
