package com.inv.inventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<String> EmptyListException(EmptyListException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The list is empty: " + e.getMessage());
    }
}
