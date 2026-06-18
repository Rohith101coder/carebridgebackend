package com.carebridge.backend.authManagement.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.dto.AuthResponse;
import com.carebridge.backend.authManagement.dto.LoginRequest;
import com.carebridge.backend.authManagement.dto.LoginResponse;
import com.carebridge.backend.authManagement.dto.RegisterRequest;
import com.carebridge.backend.authManagement.dto.ResetPasswordRequest;
import com.carebridge.backend.authManagement.dto.VerifyAndRegisterRequest;
import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.exception.InvalidOTPException;
import com.carebridge.backend.authManagement.exception.InvalidPasswordException;
import com.carebridge.backend.authManagement.exception.OTPEmailNotFoundException;
import com.carebridge.backend.authManagement.exception.OtpAlreadyUsedException;
import com.carebridge.backend.authManagement.exception.OtpExpiredException;
import com.carebridge.backend.authManagement.exception.OtpNotFoundException;
import com.carebridge.backend.authManagement.exception.UserAlreadyRegisteredException;
import com.carebridge.backend.authManagement.exception.UserNotFoundException;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.authManagement.util.JwtUtil;
import com.carebridge.backend.notificationManagement.dto.VerifyForgotPasswordOtpRequest;
import com.carebridge.backend.notificationManagement.entity.Otp;
import com.carebridge.backend.notificationManagement.repository.OtpRepository;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.notificationManagement.util.OtpUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    //generate and send otp
    public AuthResponse register(RegisterRequest request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new UserAlreadyRegisteredException("Email already registered");
        }

        String otpCode = OtpUtil.generateOtp();

        Otp otp = otpRepository.findByEmail(request.getEmail()).orElse(new Otp());

        otp.setEmail(request.getEmail());
        otp.setOtp(otpCode);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otp.setUsed(false);
        
        otpRepository.save(otp);
        System.out.println("OTP for: "+request.getEmail() + " is :"+ otpCode);
        emailService.sendOtp(request.getEmail(), otpCode);

        return new AuthResponse("OTP sent successfully");
    }

    //verify otp

    public AuthResponse verifyOtp(VerifyAndRegisterRequest request){

        Otp otp = otpRepository.findByEmail(request.getEmail())
            .orElseThrow(()-> new OtpNotFoundException("OTP not found"));
        
            if(otp.isUsed()){
                throw new OtpAlreadyUsedException("OTP already used");
            }
            if(otp.getExpiryTime().isBefore(LocalDateTime.now())){
                throw new OtpExpiredException("OTP expired");
            }

            if(!otp.getOtp().equals(request.getOtp())){
                otp.setAttempts(otp.getAttempts()+1);
                otpRepository.save(otp);
                throw new InvalidOTPException("Invalid OTP");
            }

            User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isEmailVerified(true)
                .isActive(true)
                .build();

            userRepository.save(user);

            otp.setUsed(true);
            otpRepository.save(otp);

            return new AuthResponse( "user registered successfully");
    }

      public AuthResponse resendOtp(String email) {

        Otp otp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new OTPEmailNotFoundException("No OTP request found"));

        String newOtp = OtpUtil.generateOtp();

        otp.setOtp(newOtp);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otp.setUsed(false);

        otpRepository.save(otp);

        System.out.println("Resent OTP: " + newOtp);
        emailService.sendOtp(email, newOtp);

        return new AuthResponse("OTP resent successfully");
    }

    public LoginResponse login(LoginRequest request){

            User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

            if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
                throw new InvalidPasswordException("Invalid password");
            }

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

            return new LoginResponse(token);
    }

    public AuthResponse forgotPassword(String email) {

    @SuppressWarnings("unused")
    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                new UserNotFoundException("User not found"));

    String otpCode = OtpUtil.generateOtp();

    Otp otp = otpRepository.findByEmail(email)
            .orElse(new Otp());

    otp.setEmail(email);
    otp.setOtp(otpCode);
    otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
    otp.setUsed(false);

    otpRepository.save(otp);

    emailService.sendOtp(email, otpCode);

    return new AuthResponse("OTP sent successfully");
}


public AuthResponse verifyForgotPasswordOtp(
        VerifyForgotPasswordOtpRequest request) {

    Otp otp = otpRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new OtpNotFoundException("OTP not found"));

    if (otp.isUsed()) {
        throw new OtpAlreadyUsedException("OTP already used");
    }

    if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
        throw new OtpExpiredException("OTP expired");
    }

    if (!otp.getOtp().equals(request.getOtp())) {

        otp.setAttempts(otp.getAttempts() + 1);
        otpRepository.save(otp);

        throw new InvalidOTPException("Invalid OTP");
    }

    otp.setUsed(true);
    otpRepository.save(otp);

    return new AuthResponse(
            "OTP verified successfully");
}

public AuthResponse resetPassword(
        ResetPasswordRequest request) {

    User user = userRepository.findByEmail(
            request.getEmail())
            .orElseThrow(() ->
                    new UserNotFoundException(
                            "User not found"));

    user.setPassword(
            passwordEncoder.encode(
                    request.getNewPassword()));

    userRepository.save(user);

    return new AuthResponse(
            "Password reset successfully");
}
    
}
