package com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverviewDTO {


     private String orphanageName;

    private String adminName;

    private String careBridgeId;

    private Integer childrenCount;

    private Integer activeNeedsCount;

    private Double totalDonationsAmount;

    private Integer upcomingVisitsCount;
}