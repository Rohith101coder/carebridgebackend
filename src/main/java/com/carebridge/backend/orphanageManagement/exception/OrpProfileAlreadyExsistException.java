package com.carebridge.backend.orphanageManagement.exception;

public class OrpProfileAlreadyExsistException extends RuntimeException{
    
    public OrpProfileAlreadyExsistException(String message){
        super(message);
    }
}
