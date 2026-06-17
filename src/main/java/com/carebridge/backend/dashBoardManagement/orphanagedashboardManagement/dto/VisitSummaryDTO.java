package com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto;

import java.time.LocalDate;

import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitSummaryDTO {

    private Long bookingId;

    private String donorName;

    private String purpose;

    private LocalDate visitDate;

    private String visitTime;

    private VisitBookingStatus status;
}