package com.carebridge.backend.orphanageManagement.exception;

public class UnAuthorizedUserException extends RuntimeException{
    
    public UnAuthorizedUserException(String message){
        super(message);
    }
}
