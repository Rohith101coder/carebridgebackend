package com.carebridge.backend.donationManagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donationManagement.enums.DonationType;
import com.carebridge.backend.needsManagement.enums.CategoryType;
import com.carebridge.backend.needsManagement.enums.PriorityLevel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrphanageDonationResponse {

   

    private String donationRequestId;

    private DonationStatus donationStatus;

    private DonationType donationType;

    private Integer donatedQuantity;

    private String donorMessage;

   

    private String needItemId;

    private String itemName;

    private String itemDescription;

    private CategoryType category;

    private PriorityLevel priority;

    private Integer totalRequiredQuantity;

    private Integer reservedQuantity;

    private Integer fulfilledQuantity;

    private BigDecimal pricePerQuantity;

    private List<String> productLinks;


    private String donorCareBridgeId;

    private String donorName;

    private String donorPhone;

    private String donorEmail;

   

    private LocalDateTime expectedVisitDateTime;

   

    private LocalDate expectedDeliveryDate;

    private String orderProofImageUrl;

    private String platformName;

    private String trackingId;

  

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}