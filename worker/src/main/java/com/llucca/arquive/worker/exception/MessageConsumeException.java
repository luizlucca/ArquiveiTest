package com.llucca.arquive.worker.exception;

import org.springframework.http.HttpStatus;

public class MessageConsumeException extends BaseException {
    public MessageConsumeException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public MessageConsumeException(String message, Throwable throwable) {
        super(message, throwable, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}