package com.example.backend.dto;

import com.example.backend.common.enums.ExceptionStatus;

import lombok.Getter;

@Getter
public class ErrorDto {
    private final String code;
    private final String message;

    public ErrorDto(ExceptionStatus exceptionStatus) {
        this.code = exceptionStatus.getCode();
        this.message = exceptionStatus.getMessage();
    }
}
