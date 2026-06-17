package com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationSummaryDTO {

    private Long donationId;

    private String donorName;

    private String itemName;

    private Integer quantity;

    private Double amount;

    private LocalDateTime donatedAt;
}