package org.algosolved.backend.common.exceptions;

import org.algosolved.backend.common.enums.ExceptionStatus;

public class NotFoundException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public NotFoundException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
