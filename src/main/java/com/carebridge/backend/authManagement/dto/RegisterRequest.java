package com.carebridge.backend.authManagement.dto;

import com.carebridge.backend.common.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    private Role role;

}
