package com.example.javaexercise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> handleEmployeeNotFoundException(EntityNotFoundException exception){
        ApiException apiException = new ApiException(
                exception.getMessage(),
                HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(
                apiException,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CantAssignSameEmployeeException.class)
    public ResponseEntity<Object> handleCantAssignSameEmployeeException(CantAssignSameEmployeeException exception){
        ApiException apiException = new ApiException(
                exception.getMessage(),
                HttpStatus.CONFLICT);

        return new ResponseEntity<>(
                apiException,
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EmployeeLoopRelationshipException.class)
    public ResponseEntity<Object> handleEmployeeLoopRelationshipException(EmployeeLoopRelationshipException exception){
        ApiException apiException = new ApiException(
                exception.getMessage(),
                HttpStatus.CONFLICT);

        return new ResponseEntity<>(
                apiException,
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoParameterProvidedException.class)
    public ResponseEntity<Object> handleNoParametrProvidedException(NoParameterProvidedException exception){
        ApiException apiException = new ApiException(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                apiException,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OrganizationAlreadyExistException.class)
    public ResponseEntity<Object> handleOrganizationAlreadyExistException(OrganizationAlreadyExistException exception){
        ApiException apiException = new ApiException(
                exception.getMessage(),
                HttpStatus.CONFLICT);

        return new ResponseEntity<>(
                apiException,
                HttpStatus.CONFLICT);
    }
}
