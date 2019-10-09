package com.llucca.arquivei.exception;

import org.springframework.http.HttpStatus;

public class DatabaseAccessException extends BaseException {
    public DatabaseAccessException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public DatabaseAccessException(String message, Throwable throwable) {
        super(message, throwable, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
