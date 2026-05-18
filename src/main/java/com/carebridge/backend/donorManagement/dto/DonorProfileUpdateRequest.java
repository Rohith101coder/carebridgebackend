package com.carebridge.backend.donorManagement.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorProfileUpdateRequest {
    
    private String Designation;

    private String houseNum;

    private String village;

    private String mandal;

    private String district;

    private String state;

    private String country;

    private String phone;

    private MultipartFile profilePic;

    private MultipartFile panPhoto;
}
