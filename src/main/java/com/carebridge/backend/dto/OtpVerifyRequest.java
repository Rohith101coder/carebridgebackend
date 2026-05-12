package com.carebridge.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OtpVerifyRequest {
    
    @Email
    private String email;

    @NotBlank
    private String otp;
}
