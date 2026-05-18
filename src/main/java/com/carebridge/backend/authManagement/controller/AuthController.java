package com.carebridge.backend.authManagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.authManagement.dto.AuthResponse;
import com.carebridge.backend.authManagement.dto.LoginRequest;
import com.carebridge.backend.authManagement.dto.LoginResponse;
import com.carebridge.backend.authManagement.dto.RegisterRequest;
// import com.carebridge.backend.authManagement.dto.VerifyAndRegisterRequest;
import com.carebridge.backend.authManagement.service.AuthService;
// import com.carebridge.backend.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
