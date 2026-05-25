package com.carebridge.backend.donationManagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.carebridge.backend.donationManagement.enums.DonationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationRequestDTO {

    private String needItemId;

    private Integer quantity;

    private DonationType donationType;

    // IN_PERSON

    private LocalDateTime expectedVisitDateTime;

    // ONLINE_ORDER

    private LocalDate expectedDeliveryDate;

    private MultipartFile orderProofImage;

    private String platformName;

    private String trackingId;

    private String message;

    private String orderPicString;
}