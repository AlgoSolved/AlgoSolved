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
    public BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public static <T> BaseResponse<T> success(String code, String message) {
        return new BaseResponse<>(code, message, null);
    }

    public static <T> BaseResponse<T> success(String code, String message, T data) {
        return new BaseResponse<>(code, message, data);
    }
}
