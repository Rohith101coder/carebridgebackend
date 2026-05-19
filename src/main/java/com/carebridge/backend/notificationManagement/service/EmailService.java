package com.carebridge.backend.notificationManagement.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;

    public void sendOtp(String toEmail, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("CareBridge OTP Verification");
        message.setText("Your OTP is: "+otp+ "\nValid for 5 minutes.");

        mailSender.send(message);
    }

    public void donorProfileNotification(String toEmail, String donorName){
        String subject = "Thank You for Joining CareBridge";

        String body = """
                 Dear %s,

        Thank you for completing your donor profile on CareBridge.
        We truly appreciate your willingness to support and make a positive impact in the lives of children and orphanages.

        Your profile has been successfully submitted and is currently under verification by our team.

        Profile Status: Pending Verification

        Once the verification process is completed, you will receive another update from us regarding your approval status.

        We are grateful to have compassionate people like you as part of the CareBridge community.

        Warm regards,
        Team CareBridge
                """.formatted(donorName);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        
    }

    public void orpProfileNotification(String toEmail, String orpAdminName){

        String subject = "Thank You for Registering Your Orphanage with CareBridge";
        String body = """
                Dear %s,

Greetings from CareBridge.

Thank you for successfully completing your orphanage profile registration on CareBridge. We sincerely appreciate your effort and commitment toward supporting and caring for children in need.

Your orphanage profile has been successfully submitted and is currently under review by our verification team.

Profile Status: Pending Verification

Once the verification process is completed, you will receive an update regarding your approval status and further onboarding steps.

We are honored to have your orphanage as part of the CareBridge community. Together, we aim to create a transparent, trusted, and impactful support system for orphanages and donors.

Thank you once again for joining hands with CareBridge.

Warm regards,
Team CareBridge
                """.formatted(orpAdminName);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(toEmail);
                message.setSubject(subject);
                message.setText(body);
                mailSender.send(message);
    }
}
