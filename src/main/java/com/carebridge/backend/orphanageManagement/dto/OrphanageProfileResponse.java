package com.carebridge.backend.orphanageManagement.dto;

import java.time.LocalDateTime;

import com.carebridge.backend.common.enums.VerificationStatus;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrphanageProfileResponse {

    private String carebridgeId;

    private String orphanageName;

    private String adminName;

    private String village;

    private String mandal;

    private String district;

    private String state;

    private String country;

    private Integer numberOfChildren;

    private String orphanageEmail;

    private boolean orphanageEmailVerified;

    private String orphanagePhone;

    private String websiteLink;

    private String socialMediaLinks;

    private String darpanId;

    private String panNumber;

    private String panPhoto;

    private String jjActCertificatePhoto;

    private String orphanageProfilePic;

    private String adminProfilePic;

    private VerificationStatus verificationStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}