package com.example.backend.dto;

import com.example.backend.common.response.ResponseStatus;

import lombok.Getter;

@Getter
public class ErrorDto {
    private final String code;
    private final String message;

    public ErrorDto(ResponseStatus responseStatus) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
    }
}
