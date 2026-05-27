package com.carebridge.backend.visitbookingManagement.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitBookingRequest {

   
    private String slotId;

   
    private Integer numberOfVisitors;

    private String message;
}
