package com.carebridge.backend.dashBoardManagement.dto;

import lombok.Data;

@Data
public class DonorStats {
    
    private int totalDonationAmmount;
    private int needsSupported;
    private int totalVisits;
    private int orphanageSupported;
}
