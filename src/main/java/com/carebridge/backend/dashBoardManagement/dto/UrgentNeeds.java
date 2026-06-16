package com.carebridge.backend.dashBoardManagement.dto;

import com.carebridge.backend.needsManagement.enums.CategoryType;

import lombok.Data;

@Data
public class UrgentNeeds {
    
    private String needId;
    private String title;
    private String orpName;
    private String quantityNeeded;
    private CategoryType category;
    private String priorityLevel;
}
