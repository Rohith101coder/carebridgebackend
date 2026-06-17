package com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto;

import com.carebridge.backend.needsManagement.enums.PriorityLevel;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NeedSummaryDTO {

    private Long needId;

    private String itemName;

    private Integer requiredQuantity;

    private Integer receivedQuantity;

    private Integer remainingQuantity;

    private PriorityLevel priority;
}