package org.algosolved.backend.common.exceptions;

import lombok.Getter;

import org.algosolved.backend.common.enums.ExceptionStatus;

@Getter
public class NotFoundException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public NotFoundException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
