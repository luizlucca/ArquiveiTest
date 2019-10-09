package com.llucca.arquive.worker.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArquiveiException {
    private int statusCode;
    private String message;
}
