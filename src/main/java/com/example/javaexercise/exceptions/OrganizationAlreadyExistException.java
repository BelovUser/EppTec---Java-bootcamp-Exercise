package com.example.javaexercise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class OrganizationAlreadyExistException extends RuntimeException{
    public OrganizationAlreadyExistException(String message) {
        super(message);
    }

    public OrganizationAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
