package com.carebridge.backend.authManagement.exception;

public class OtpExpiredException extends RuntimeException {
    
    public OtpExpiredException(String message){
        super(message);
    }
}
