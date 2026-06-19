package com.carebridge.backend.visitbookingManagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;

import lombok.Data;

@Data
public class ApprovedVisitResponse {

    private String bookingId;

    private String slotId;

    private String donorCareBridgeId;

    private Integer numberOfVisitors;

    private String message;

    private VisitBookingStatus bookingStatus;

    // slot info
    private LocalDate slotDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}