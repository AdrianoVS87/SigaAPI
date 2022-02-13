package com.healthchess.sigaapi.service.exceptions;

public class MethodArgumentTypeMismatchException extends RuntimeException {

    public MethodArgumentTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodArgumentTypeMismatchException(String message) {
        super(message);
    }
}
