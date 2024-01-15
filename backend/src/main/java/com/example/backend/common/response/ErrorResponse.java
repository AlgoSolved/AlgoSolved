package com.example.backend.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;

    @Builder
    public ErrorResponse(ResponseStatus responseStatus) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
    }

    public static BaseResponse failure(ResponseStatus responseStatus) {
        return BaseResponse.builder().responseStatus(responseStatus).build();
    }
}
