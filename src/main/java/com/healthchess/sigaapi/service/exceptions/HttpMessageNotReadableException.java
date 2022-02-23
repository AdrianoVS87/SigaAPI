package com.healthchess.sigaapi.service.exceptions;

public class HttpMessageNotReadableException extends RuntimeException {

    public HttpMessageNotReadableException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpMessageNotReadableException(String message) {
        super(message);
    }

}
