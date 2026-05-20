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


    public void donorApproveNotification(String toEmail, String donorName){
        String subject = "Your CareBridge Donor Profile Has Been Approved";
        String body = """
                Dear %s,

We are delighted to inform you that your donor profile on CareBridge has been successfully verified and approved.

Profile Status: Approved

Thank you for your patience and for choosing to be a part of the CareBridge community. Your willingness to support orphanages and children in need truly makes a meaningful difference.

You can now access donor features and continue your journey of creating positive impact through CareBridge.

We deeply value your trust and support, and we look forward to building a compassionate community together.

Thank you once again for joining CareBridge.

Warm regards,
Team CareBridge
                """.formatted(donorName);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(toEmail);
                message.setSubject(subject);
                message.setText(body);

                mailSender.send(message);
    }

    public void donorRejectionNotification(String toEmail, String reason, String donorName) {

    String subject = "Update Regarding Your CareBridge Donor Profile";

    String body = """
            Dear %s,

            Thank you for showing interest in becoming a donor on CareBridge.

            After reviewing your profile and submitted details, we regret to inform you that your donor profile could not be approved at this time.

            Profile Status: Rejected

            Reason:
            %s

            You may review the mentioned reason, make the necessary corrections if applicable, and submit your profile again.

            We sincerely appreciate your willingness to support orphanages and children in need through CareBridge.

            Thank you for your understanding.

            Warm regards,
            Team CareBridge
            """.formatted(donorName, reason);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}

       public void approveOrpNotification(String toEmail, String orpAdminName, String orpName) {

    String subject = "Your Orphanage Profile Has Been Approved";

    String body = """
            Dear %s,

            Greetings from CareBridge.

            We are pleased to inform you that the orphanage profile for "%s" has been successfully verified and approved by our team.

            Profile Status: Approved

            Your orphanage is now officially part of the CareBridge community and can start accessing platform services and opportunities to connect with donors and supporters.

            Thank you for your dedication and commitment toward supporting children in need. We are honored to have your organization with us in this meaningful journey.

            We look forward to creating a positive impact together.

            Warm regards,
            Team CareBridge
            """.formatted(orpAdminName, orpName);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}

        public void rejectOrpNotification(String toEmail,
                                  String orpAdminName,
                                  String orpName,
                                  String reason) {

    String subject = "Update Regarding Your Orphanage Profile";

    String body = """
            Dear %s,

            Greetings from CareBridge.

            Thank you for registering "%s" on the CareBridge platform.

            After reviewing the submitted details and documents, we regret to inform you that your orphanage profile could not be approved at this time.

            Profile Status: Rejected

            Reason:
            %s

            You may review the mentioned reason, make the necessary corrections if applicable, and resubmit your profile for verification.

            We sincerely appreciate your efforts and commitment toward supporting children in need.

            Thank you for your understanding and interest in being part of the CareBridge community.

            Warm regards,
            Team CareBridge
            """.formatted(orpAdminName, orpName, reason);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}

}
