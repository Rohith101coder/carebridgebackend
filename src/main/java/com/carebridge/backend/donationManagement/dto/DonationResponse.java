package com.carebridge.backend.donationManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationResponse {
    

    private String message;

    private String donationId;
}
