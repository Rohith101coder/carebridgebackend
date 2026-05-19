package com.carebridge.backend.orphanageManagement.dto;

import lombok.*;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
@Data
public class OTPVerifyAndOrpProfileAdd {
    
    private String email;

    private String otp;
}
