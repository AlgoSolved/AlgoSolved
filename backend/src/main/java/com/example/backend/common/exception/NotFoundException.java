package com.example.backend.common.exception;

import com.example.backend.common.enums.ExceptionStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
    private String code;
    private String message;
    private HttpStatus httpStatus;


    public NotFoundException(ExceptionStatus exceptionStatus) {
        this.code = exceptionStatus.getCode();
        this.message = exceptionStatus.getMessage();
        this.httpStatus = exceptionStatus.getHttpStatus();
    }

}
