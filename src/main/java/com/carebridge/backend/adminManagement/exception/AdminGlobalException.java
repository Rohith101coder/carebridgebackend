package com.carebridge.backend.adminManagement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.carebridge.backend.authManagement.dto.ErrorResponse;

@RestControllerAdvice
public class AdminGlobalException {
    
    @ExceptionHandler(DonorProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDonotNotFound(
        DonorProfileNotFoundException ex, WebRequest request
    ){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrpException.class)
    public ResponseEntity<ErrorResponse> handleOrp(
        OrpException ex, WebRequest request
    ){
         ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
