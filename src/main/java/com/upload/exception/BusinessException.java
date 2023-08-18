package com.upload.exception;

public class BusinessException extends Exception {
    private final String message;

    public BusinessException(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
