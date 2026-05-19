package com.carebridge.backend.orphanageManagement.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrpErrorResponse {
     private int status;

    private String message;

    private LocalDateTime timestamp;

    private String path;

    public OrpErrorResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
