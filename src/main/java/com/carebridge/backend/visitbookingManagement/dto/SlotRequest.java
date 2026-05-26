package com.carebridge.backend.visitbookingManagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotRequest {

    // @NotNull(message = "Date is required")
    // @FutureOrPresent(message = "Date cannot be in the past")
    private LocalDate date;

    // @NotNull(message = "Start time is required")
    private LocalTime startTime;

    // @NotNull(message = "End time is required")
    private LocalTime endTime;

   

    // @NotNull(message = "Max visitors required")
    // @Min(value = 1, message = "Minimum 1 visitor required")
    private Integer maxVisitors;

  

    private String title;

    private String description;
}
