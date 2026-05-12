package com.carebridge.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.dto.ApiResponse;
import com.carebridge.backend.dto.LoginRequest;
import com.carebridge.backend.dto.LoginResponse;
// import com.carebridge.backend.dto.OtpVerifyRequest;
import com.carebridge.backend.dto.RegisterRequest;
import com.carebridge.backend.dto.VerifyAndRegisterRequest;
import com.carebridge.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegisterRequest request){
        return new ApiResponse(authService.register(request));
    }

    @PostMapping("/verify")
    public ApiResponse verify(@RequestBody VerifyAndRegisterRequest verifyRegRequest){
        return new ApiResponse(authService.verifyOtp(verifyRegRequest));
    }

    @PostMapping("/resend")
    public ApiResponse resend(@RequestParam String email){
        return new ApiResponse(authService.resendOtp(email));
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
