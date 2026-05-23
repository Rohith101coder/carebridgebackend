package com.carebridge.backend.needsManagement.exception;

import org.springframework.http.HttpStatus;
// import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.carebridge.backend.authManagement.dto.ErrorResponse;

@RestControllerAdvice
public class NeedGlobalException {

    @ExceptionHandler(OrpNotVerified.class)
    public ResponseEntity<ErrorResponse> handleOrpNotFound(
        OrpNotVerified ex, WebRequest request
    ){

        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
}
