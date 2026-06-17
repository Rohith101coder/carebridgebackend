package com.carebridge.backend.dashBoardManagement.donordashboardManagement.dto;

// import java.time.LocalDate;
// import java.time.LocalTime;

import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;

import lombok.Data;

@Data
public class UpcomingBookings {
    

    private String bookingId;
    private String title;
    private String orpName;
    private String bookingDate;
    private String bookingTime;
    private VisitBookingStatus bookingStatus;
}
