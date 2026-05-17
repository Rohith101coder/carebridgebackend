package com.carebridge.backend.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
// import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorProfileRequest {
    
    @NotBlank(message  = "name required")
    private String name;

    private LocalDate dob;

    @NotBlank(message = "designation required")
    private String designation;

    private String houseNum;

    private String mandal;

    private String village;

    private String district;

    private String state;

    private String country;

    
    private String phone;

    private MultipartFile profilePic;

    @Pattern(
    regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$",
    message = "Invalid PAN format"
)
    private String panNumber;

    
    private MultipartFile panPhoto;
}
