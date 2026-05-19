package com.carebridge.backend.orphanageManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.carebridge.backend.orphanageManagement.dto.OrpErrorResponse;

@RestControllerAdvice
public class OrpGlobalExceptionHandler {
    
    @ExceptionHandler(UnAuthorizedUserException.class)
    public ResponseEntity<OrpErrorResponse> handleUnAuthorizedUser(
        UnAuthorizedUserException ex, WebRequest request
    ){
        OrpErrorResponse error =  new OrpErrorResponse(
            HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(OrpProfileAlreadyExsistException.class)
    public ResponseEntity<OrpErrorResponse> handleOrpProfileAlreadyExsist(
        OrpProfileAlreadyExsistException ex, WebRequest request
    ){
        OrpErrorResponse error = new OrpErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
