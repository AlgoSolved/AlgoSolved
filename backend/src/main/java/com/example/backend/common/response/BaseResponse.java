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
    public BaseResponse(ResponseStatus responseStatus, T data) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.data = data;
    }

    public static <T> BaseResponse<T> success(ResponseStatus responseStatus) {
        return BaseResponse.<T>builder().responseStatus(responseStatus).build();
    }

    public static <T> BaseResponse<T> success(ResponseStatus responseStatus, T data) {
        return BaseResponse.<T>builder().responseStatus(responseStatus).data(data).build();
    }
}
