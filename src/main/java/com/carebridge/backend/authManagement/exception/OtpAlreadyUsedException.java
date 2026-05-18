package com.carebridge.backend.authManagement.exception;

public class OtpAlreadyUsedException extends RuntimeException {
    
    public OtpAlreadyUsedException(String message){
        super(message);
    }
}
