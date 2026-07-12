package com.carebridge.backend.donorManagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.carebridge.backend.common.enums.DonorSubscriptionStatus;
import com.carebridge.backend.common.enums.VerificationStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonorProfileResponseDto {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String designation;

    private String houseNum;
    private String careBridgeID;

    private String village;
    private String mandal;
    private String district;
    private String state;
    private String country;

    private String phone;

    private String profilePic;

    private String panNumber;
    private String panPhoto;

    private VerificationStatus donorStatus;
    private DonorSubscriptionStatus subscriptionStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
