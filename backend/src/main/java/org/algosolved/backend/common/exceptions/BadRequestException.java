package org.algosolved.backend.common.exceptions;

import lombok.Getter;
import org.algosolved.backend.common.enums.ExceptionStatus;

@Getter
public class BadRequestException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public BadRequestException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
