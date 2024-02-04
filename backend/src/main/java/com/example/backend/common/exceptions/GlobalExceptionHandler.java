package com.example.backend.common.exceptions;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(ExceptionStatus exceptionStatus) {
        BaseResponse exceptionResponse = BaseResponse.builder().code(exceptionStatus.getCode())
                .message(
                        exceptionStatus.getMessage()).build();

        return new ResponseEntity(exceptionResponse, exceptionStatus.getHttpStatus());
    }

}