package com.carebridge.backend.authManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.carebridge.backend.authManagement.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
        UserNotFoundException ex, WebRequest request
    ){
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(error,
            HttpStatus.NOT_FOUND
        );

    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse>
            handleInvalidPassword(
                InvalidPasswordException ex, WebRequest request
            ){
                ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                 ex.getMessage(), 
                request.getDescription(false).replace("uri=", ""));

                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            }


            @ExceptionHandler(UserAlreadyRegisteredException.class)
            public ResponseEntity<ErrorResponse> handleUserAlreadyRegistered(
                UserAlreadyRegisteredException ex, WebRequest request
            ){
                ErrorResponse error = new ErrorResponse(
                    HttpStatus.ALREADY_REPORTED.value(),
                    ex.getMessage(),
                    request.getDescription(false).replace("uri=", "")
                );

                return new ResponseEntity<>(error, HttpStatus.ALREADY_REPORTED);
            }


            @ExceptionHandler(OtpNotFoundException.class)
            public ResponseEntity<ErrorResponse> handleOtpNotFound(
                OtpNotFoundException ex, WebRequest request
            ){
                ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false).replace("uri=", ""));

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            @ExceptionHandler(OtpExpiredException.class)
            public ResponseEntity<ErrorResponse> handleOtpExpired(
                OtpExpiredException ex, WebRequest request
            ){
                ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), request.getDescription(false).replace("uri=", ""));

                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            }

            @ExceptionHandler(OtpAlreadyUsedException.class)
            public ResponseEntity<ErrorResponse> handleOtpExpired(
                OtpAlreadyUsedException ex, WebRequest request
            ){
                ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), request.getDescription(false).replace("uri=", ""));

                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
            }
            @ExceptionHandler(InvalidOTPException.class)
            public ResponseEntity<ErrorResponse> handleOtpExpired(
                InvalidOTPException ex, WebRequest request
            ){
                ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), request.getDescription(false).replace("uri=", ""));

                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
            }
            @ExceptionHandler(OTPEmailNotFoundException.class)
            public ResponseEntity<ErrorResponse> handleOtpExpired(
                OTPEmailNotFoundException ex, WebRequest request
            ){
                ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false).replace("uri=", ""));

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }




}
