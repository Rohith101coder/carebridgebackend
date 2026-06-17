package com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto;

import java.time.LocalDateTime;

import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.enums.ActivityType;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {

    private ActivityType activityType;

    private String title;

    private String description;

    private LocalDateTime createdAt;
}