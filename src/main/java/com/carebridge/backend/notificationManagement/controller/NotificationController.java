package com.carebridge.backend.notificationManagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.authManagement.dto.VerifyAndRegisterRequest;
import com.carebridge.backend.authManagement.service.AuthService;
// import com.carebridge.backend.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class NotificationController {

    private final AuthService authService;
    @PostMapping("/verify")
    public String verify(@RequestBody VerifyAndRegisterRequest verifyRegRequest){
        return authService.verifyOtp(verifyRegRequest);
    }

    @PostMapping("/resend")
    public String resend(@RequestParam String email){
        return authService.resendOtp(email);
    }

}
