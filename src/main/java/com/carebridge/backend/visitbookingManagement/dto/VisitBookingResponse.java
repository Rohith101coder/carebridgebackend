package com.carebridge.backend.visitbookingManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitBookingResponse {
    
    private String message;

    private String bookingId;
}
