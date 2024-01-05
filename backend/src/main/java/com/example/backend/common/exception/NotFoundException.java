package com.example.backend.common.exception;

import com.example.backend.common.enums.ExceptionStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private ExceptionStatus exceptionStatus;

    public NotFoundException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
