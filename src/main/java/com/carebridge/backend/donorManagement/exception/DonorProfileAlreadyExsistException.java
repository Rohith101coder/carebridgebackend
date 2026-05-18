package com.carebridge.backend.donorManagement.exception;

public class DonorProfileAlreadyExsistException extends RuntimeException {
    
    public DonorProfileAlreadyExsistException(String message){
        super(message);
    }
}
