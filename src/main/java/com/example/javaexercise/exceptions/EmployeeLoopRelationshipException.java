package com.example.javaexercise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmployeeLoopRelationshipException extends RuntimeException{
    public EmployeeLoopRelationshipException(String message) {
        super(message);
    }

    public EmployeeLoopRelationshipException(String message, Throwable cause) {
        super(message, cause);
    }
}
