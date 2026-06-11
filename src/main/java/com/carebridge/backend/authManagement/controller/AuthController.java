package com.carebridge.backend.authManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/")
    public String health() {
        return "CareBridge Backend Running";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        AuthResponse response= authService.register(request);
        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
