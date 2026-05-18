package com.carebridge.backend.orphanageManagement.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrphanageProfileRequest {
    
    @NotBlank
    private String orphanageName;

    @NotBlank
    private String admin;

    private String village;

    private String mandal;

    @NotBlank
    private String district;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotBlank
    private Integer numberOfChildren;

    @Email
    private String orphanageEmail;

    @NotBlank
    private String orphanagePhone;

    private String websiteLink;

    private String socialMediaLinks;

    @NotBlank
    private String darpanId;

    @NotBlank
    private String panNumber;

    @NotBlank
    private MultipartFile panPhoto;

    @NotBlank
    private MultipartFile jjActCertificatePhoto;

    private MultipartFile orphanageProfilePic;

    private MultipartFile adminProfilePic;


}
