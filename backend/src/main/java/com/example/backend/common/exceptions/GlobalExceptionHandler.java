package com.example.backend.common.exceptions;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.response.BaseResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(ExceptionStatus exceptionStatus) {
        BaseResponse exceptionResponse =
                BaseResponse.builder()
                        .code(exceptionStatus.getCode())
                        .message(exceptionStatus.getMessage())
                        .build();

        return new ResponseEntity(exceptionResponse, exceptionStatus.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse> handleUsernameMismatchException(
            ExceptionStatus exceptionStatus) {
        BaseResponse exceptionResponse =
                BaseResponse.builder()
                        .code(exceptionStatus.getCode())
                        .message(exceptionStatus.getMessage())
                        .build();

        return new ResponseEntity<>(exceptionResponse, exceptionStatus.getHttpStatus());
    }
}
