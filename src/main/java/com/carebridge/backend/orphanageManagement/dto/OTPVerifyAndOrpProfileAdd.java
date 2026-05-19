package com.carebridge.backend.orphanageManagement.dto;

// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OTPVerifyAndOrpProfileAdd {
 
    private String email;

    private String otp;
}
