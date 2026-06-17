package com.carebridge.backend.landingPagedata.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrphanageSummaryResponse {
    private String carebridgeId;
    private String orphanageName;
    private String location;
    private Integer numberOfChildren;
    private Integer activeNeeds;
    private String orphanageProfilePic;
    private String verificationStatus;
    private String description;
}
