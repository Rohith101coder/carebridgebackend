package com.carebridge.backend.orphanageManagement.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrphanageProfileUpdateRequest {
    
     private String adminName;

    private String village;

    private String mandal;

    private String district;

    private String state;

    private String country;

    private Integer numberOfChildren;

    private String orphanagePhone;

    private String websiteLink;

    private String socialMediaLinks;

    private MultipartFile orphanageProfilePic;

    private MultipartFile adminProfilePic;


}
