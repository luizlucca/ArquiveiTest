package com.llucca.arquive.worker.exception;

public class BaseException extends RuntimeException {

    private int statusCode;

    public BaseException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public BaseException(String message, Throwable throwable, Integer statusCode) {
        super(message, throwable);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}