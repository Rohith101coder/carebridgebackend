package com.carebridge.backend.donorManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.carebridge.backend.authManagement.dto.ErrorResponse;

@RestControllerAdvice
public class DonorGlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
        UserNotFoundException ex, WebRequest request
    ){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false).replace("uri=",""));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DonorProfileAlreadyExsistException.class)
    public ResponseEntity<ErrorResponse> handleDonorProfileAlredyExsists(
        DonorProfileAlreadyExsistException ex, WebRequest request
    ){
         ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), request.getDescription(false).replace("uri=",""));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileIssueException.class)
    public ResponseEntity<ErrorResponse> handleFileIssue(
        FileIssueException ex, WebRequest request
    ){
         ErrorResponse error = new ErrorResponse(HttpStatus.CONTENT_TOO_LARGE.value(), ex.getMessage(), request.getDescription(false).replace("uri=",""));
        return new ResponseEntity<>(error, HttpStatus.CONTENT_TOO_LARGE);
    }

    @ExceptionHandler(DonorProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDonorProfileNotFound(
        DonorProfileNotFoundException ex,
        WebRequest request
    ){
         ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false).replace("uri=",""));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
