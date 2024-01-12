package com.example.backend.common.exception;

import com.example.backend.common.response.ResponseStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private ResponseStatus responseStatus;

    public NotFoundException(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
