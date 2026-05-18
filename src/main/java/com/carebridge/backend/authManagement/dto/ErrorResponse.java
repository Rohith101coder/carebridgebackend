package com.carebridge.backend.authManagement.dto;

import java.time.LocalDateTime;

// import lombok.AllArgsConstructor;
import lombok.Getter;
// import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter


public class ErrorResponse {
    
    private int status;

    private String message;

    private LocalDateTime timestamp;

    private String path;

    public ErrorResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
