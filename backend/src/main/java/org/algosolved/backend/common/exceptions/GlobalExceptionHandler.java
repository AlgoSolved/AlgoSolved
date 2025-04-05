package org.algosolved.backend.common.exceptions;

import org.algosolved.backend.common.enums.ExceptionStatus;
import org.algosolved.backend.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException e) {
        ExceptionStatus exceptionStatus = e.getExceptionStatus();

        BaseResponse exceptionResponse =
                BaseResponse.builder()
                        .code(exceptionStatus.getCode())
                        .message(exceptionStatus.getMessage())
                        .build();

        return new ResponseEntity(exceptionResponse, exceptionStatus.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse> handleUsernameMismatchException(
            BadRequestException e) {
        ExceptionStatus exceptionStatus = e.getExceptionStatus();

        BaseResponse exceptionResponse =
                BaseResponse.builder()
                        .code(exceptionStatus.getCode())
                        .message(exceptionStatus.getMessage())
                        .build();

        return new ResponseEntity<>(exceptionResponse, exceptionStatus.getHttpStatus());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<BaseResponse> handleUnAuthorizedException(
            JwtException e) {
        ExceptionStatus exceptionStatus = e.getExceptionStatus();

        BaseResponse exceptionResponse =
                BaseResponse.builder()
                        .code(exceptionStatus.getCode())
                        .message(exceptionStatus.getMessage())
                        .build();

        return new ResponseEntity<>(exceptionResponse, exceptionStatus.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleInternalException(Exception e) {

        BaseResponse exceptionResponse =
                BaseResponse.builder()
                        .code("5000")
                        .message(e.getMessage())
                        .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
