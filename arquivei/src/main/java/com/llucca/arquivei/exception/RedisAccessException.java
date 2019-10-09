package com.llucca.arquivei.exception;

import org.springframework.http.HttpStatus;

public class RedisAccessException extends BaseException {
    public RedisAccessException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public RedisAccessException(String message, Throwable throwable) {
        super(message, throwable, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
