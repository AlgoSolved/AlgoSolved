package com.example.backend.dto;

import com.example.backend.common.enums.ExceptionStatus;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ErrorDto {
    private String code;
    private String message;

    public ErrorDto(ExceptionStatus exceptionStatus) {
        this.code = exceptionStatus.getCode();
        this.code = exceptionStatus.getMessage();
    }

}
