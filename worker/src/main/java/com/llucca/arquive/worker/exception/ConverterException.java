package com.llucca.arquive.worker.exception;

import org.springframework.http.HttpStatus;

public class ConverterException extends BaseException {
    public ConverterException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }

    public ConverterException(String message, Throwable throwable) {
        super(message, throwable, HttpStatus.BAD_REQUEST.value());
    }
}
