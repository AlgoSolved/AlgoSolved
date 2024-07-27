package org.algosolved.backend.common.exceptions;

import org.algosolved.backend.common.enums.ExceptionStatus;

public class BadRequestException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public BadRequestException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
