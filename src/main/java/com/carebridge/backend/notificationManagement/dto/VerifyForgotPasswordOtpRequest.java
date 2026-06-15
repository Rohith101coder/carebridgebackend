package com.carebridge.backend.notificationManagement.dto;

import lombok.Data;

@Data
public class VerifyForgotPasswordOtpRequest {

    private String email;
    private String otp;
}