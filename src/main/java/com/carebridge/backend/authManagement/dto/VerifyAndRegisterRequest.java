package com.carebridge.backend.authManagement.dto;

import com.carebridge.backend.common.enums.Role;

import lombok.Data;

@Data
public class VerifyAndRegisterRequest {
    
     private String email;
    private String otp;

    private String name;
    private String password;
    private Role role;
}
