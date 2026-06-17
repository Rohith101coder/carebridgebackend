package com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverviewDTO {

    private Integer childrenCount;

    private Integer activeNeedsCount;

    private Double totalDonationsAmount;

    private Integer upcomingVisitsCount;
}