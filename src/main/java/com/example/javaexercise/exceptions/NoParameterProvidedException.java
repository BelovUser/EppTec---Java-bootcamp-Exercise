package com.example.javaexercise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoParameterProvidedException extends RuntimeException {
    public NoParameterProvidedException(String message) {
        super(message);
    }

    public NoParameterProvidedException(String message, Throwable cause) {
        super(message, cause);
    }
}
