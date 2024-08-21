package org.algosolved.backend.common.exceptions;

import org.algosolved.backend.common.enums.ExceptionStatus;

public class JwtException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public JwtException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
