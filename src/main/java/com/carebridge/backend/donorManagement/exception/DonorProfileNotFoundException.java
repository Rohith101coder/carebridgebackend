package com.carebridge.backend.donorManagement.exception;

public class DonorProfileNotFoundException extends RuntimeException{
    
    public DonorProfileNotFoundException(String message){
        super(message);
    }
}
