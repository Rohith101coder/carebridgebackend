package com.carebridge.backend.needsManagement.exception;

public class ItemAreadyExsist extends RuntimeException {
    
    public ItemAreadyExsist(String message){
        super(message);
    }
}
