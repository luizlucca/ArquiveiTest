package com.llucca.arquivei.exception;

import org.springframework.http.HttpStatus;

public class ArquiveApiException extends BaseException {
    public ArquiveApiException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public ArquiveApiException(String message, Throwable throwable) {
        super(message, throwable, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
