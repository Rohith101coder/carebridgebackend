package com.carebridge.backend.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// import com.carebridge.backend.dto.OtpVerifyRequest;
import com.carebridge.backend.dto.RegisterRequest;
import com.carebridge.backend.dto.VerifyAndRegisterRequest;
import com.carebridge.backend.entity.Otp;
import com.carebridge.backend.entity.User;
import com.carebridge.backend.repository.OtpRepository;
import com.carebridge.backend.repository.UserRepository;
import com.carebridge.backend.util.OtpUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    //generate and send otp
    public String register(RegisterRequest request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
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

        return "OTP sent successfully";
    }

    //verify otp

    public String verifyOtp(VerifyAndRegisterRequest request){

        Otp otp = otpRepository.findByEmail(request.getEmail())
            .orElseThrow(()-> new RuntimeException("OTP not found"));
        
            if(otp.isUsed()){
                throw new RuntimeException("OTP already used");
            }
            if(otp.getExpiryTime().isBefore(LocalDateTime.now())){
                throw new RuntimeException("OTP expired");
            }

            if(!otp.getOtp().equals(request.getOtp())){
                otp.setAttempts(otp.getAttempts()+1);
                otpRepository.save(otp);
                throw new RuntimeException("Invalid OTP");
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

            return "user registered successfully";
    }

      public String resendOtp(String email) {

        Otp otp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No OTP request found"));

        String newOtp = OtpUtil.generateOtp();

        otp.setOtp(newOtp);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otp.setUsed(false);

        otpRepository.save(otp);

        System.out.println("Resent OTP: " + newOtp);
        emailService.sendOtp(email, newOtp);

        return "OTP resent successfully";
    }
    
}
