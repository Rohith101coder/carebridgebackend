package com.carebridge.backend.adminManagement.exception;

public class DonorProfileNotFoundException extends RuntimeException{
    
    public DonorProfileNotFoundException(String message){
        super(message);
    }
}
