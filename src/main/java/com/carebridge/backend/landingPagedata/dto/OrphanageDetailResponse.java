package com.carebridge.backend.landingPagedata.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrphanageDetailResponse {
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
    private Boolean orphanageEmailVerified;
    private String orphanagePhone;
    private String websiteLink;
    private String socialMediaLinks;
    private String darpanId;
    private String panNumber;
    private String panPhoto;
    private String jjActCertificatePhoto;
    private String orphanageProfilePic;
    private String adminProfilePic;
    private String verificationStatus;
    private String location;
    private String description;
    private Integer activeNeeds;
    private List<NeedSummaryResponse> needs;
}
