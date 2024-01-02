package com.example.backend.common.exception;

import com.example.backend.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException notFoundException) {
        ErrorDto errorDto = new ErrorDto(notFoundException.getExceptionStatus());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

}
