package com.carebridge.backend.notificationManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.authManagement.dto.AuthResponse;
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
    public ResponseEntity<AuthResponse> verify(@RequestBody VerifyAndRegisterRequest verifyRegRequest){
        AuthResponse reponse = authService.verifyOtp(verifyRegRequest);
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("/resend")
    public ResponseEntity<AuthResponse> resend(@RequestParam String email){
        AuthResponse response = authService.resendOtp(email);
        return ResponseEntity.ok(response);
    }

}
