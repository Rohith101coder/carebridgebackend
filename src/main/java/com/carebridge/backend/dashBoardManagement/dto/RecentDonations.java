package com.carebridge.backend.dashBoardManagement.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RecentDonations {
    
    private String donationId;
    private String itemName;
    private String orpName;
    private String donationDate; 
    private  String imagePath; 

}
