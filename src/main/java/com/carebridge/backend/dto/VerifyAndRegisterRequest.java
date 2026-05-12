package com.carebridge.backend.dto;

import com.carebridge.backend.enums.Role;

import lombok.Data;

@Data
public class VerifyAndRegisterRequest {
    
     private String email;
    private String otp;

    private String name;
    private String password;
    private Role role;
}
