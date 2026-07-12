package com.carebridge.backend.visitbookingManagement.dto;

import java.time.LocalDateTime;

import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitBookingDTO {

    private String bookingId;

    private String slotId;

    private String orphanageCareBridgeId;

    private Integer numberOfVisitors;

    private String message;

    private VisitBookingStatus bookingStatus;

    private LocalDateTime createdAt;
}