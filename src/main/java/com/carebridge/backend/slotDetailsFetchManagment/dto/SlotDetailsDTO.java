package com.carebridge.backend.slotDetailsFetchManagment.dto;

import lombok.Data;

@Data
public class SlotDetailsDTO {
    

    private String slotId;

    private String date;

    private String startTime;

    private String endTime;

    private String capacity;

    private String availableSeats;

    private String isBooked;
}
