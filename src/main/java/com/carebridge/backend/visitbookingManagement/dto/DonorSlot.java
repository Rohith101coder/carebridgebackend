package com.carebridge.backend.visitbookingManagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.carebridge.backend.visitbookingManagement.enums.SlotStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorSlot {
    
    private String slotId;

    private LocalDate date;

    private  LocalTime starTime;

    private  LocalTime endTime;

    private Integer maxVisitors;

    private SlotStatus slotStatus;
}
