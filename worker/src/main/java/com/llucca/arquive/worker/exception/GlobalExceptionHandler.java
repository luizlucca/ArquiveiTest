package com.llucca.arquive.worker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public ResponseEntity handleBaseException(BaseException be) {
        return ResponseEntity.status(HttpStatus.valueOf(be.getStatusCode())).body(new ArquiveiException(be.getStatusCode(), be.getMessage()));
    }
}
