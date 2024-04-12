package com.example.javaexercise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CantAssignSameEmployeeException extends RuntimeException{
    public CantAssignSameEmployeeException(String message) {
        super(message);
    }

    public CantAssignSameEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
