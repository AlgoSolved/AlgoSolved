package org.algosolved.backend.common.exceptions;

import lombok.Getter;

import org.algosolved.backend.common.enums.ExceptionStatus;

@Getter
public class JwtException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public JwtException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
