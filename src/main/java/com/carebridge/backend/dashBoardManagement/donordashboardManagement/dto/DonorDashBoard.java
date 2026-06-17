package com.carebridge.backend.dashBoardManagement.donordashboardManagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DonorDashBoard {
    
    private DonorData donorData;
    private DonorStats donorStats;
    private DonorImpactSummary donorImpactSummary;
    private List<RecentDonations> recentDonations;
    private List<UpcomingBookings> upcomingBookings;
    private List<UrgentNeeds> urgentNeeds;
}
