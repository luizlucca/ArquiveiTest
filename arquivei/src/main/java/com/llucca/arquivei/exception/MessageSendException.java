package com.llucca.arquivei.exception;

import org.springframework.http.HttpStatus;

public class MessageSendException extends BaseException {
    public MessageSendException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public MessageSendException(String message, Throwable throwable) {
        super(message, throwable, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}