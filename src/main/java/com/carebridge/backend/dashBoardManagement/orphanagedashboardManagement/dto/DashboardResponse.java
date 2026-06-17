package com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto;

import java.util.List;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private OverviewDTO overview;

    private List<NeedSummaryDTO> activeNeeds;

    private List<VisitSummaryDTO> upcomingVisits;

    private List<DonationSummaryDTO> recentDonations;

    private List<ActivityDTO> recentActivities;
}
