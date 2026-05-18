package com.carebridge.backend.authManagement.exception;


public class OTPEmailNotFoundException extends RuntimeException{
    
    public OTPEmailNotFoundException(String message){
        super(message);
    }
}
