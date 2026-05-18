package com.carebridge.backend.authManagement.exception;

public class OtpNotFoundException extends RuntimeException{
    
    public OtpNotFoundException(String message){
        super(message);
    }
}
