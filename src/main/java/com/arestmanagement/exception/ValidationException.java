package com.arestmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*@Getter
@AllArgsConstructor*/
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }


    /* private final String message;*/
}
