package com.carebridge.backend.landingPagedata.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NeedSummaryResponse {
    private String needItemId;
    private String name;
    private String description;
    private String category;
    private Integer quantity;
    private Integer fulfilledQuantity;
    private Integer reservedQuantity;
    private String priority;
    private BigDecimal pricePerQuantity;
    private String orphanageCareBridgeId;
    private String orphanageName;
    private String location;
    private String orphanageProfilePic;
    private Integer remainingQuantity;
    private Integer progress;
}
