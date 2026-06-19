package com.carebridge.backend.notificationManagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
// import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

// import java.time.LocalDate;

// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.carebridge.backend.notificationManagement.util.EmailMessages;
import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    
//     private final JavaMailSender mailSender;

         @Value("${brevo.api.key}")
    private String apiKey;

    private final RestClient restClient =
            RestClient.builder()
                    .baseUrl("https://api.brevo.com")
                    .build();

    private void sendEmail(
            String toEmail,
            String subject,
            String htmlContent) {

        Map<String, Object> payload = Map.of(
                "sender", Map.of(
                        "name", "CareBridge",
                        "email", "carebridge086@gmail.com"
                ),
                "to", List.of(
                        Map.of("email", toEmail)
                ),
                "subject", subject,
                "htmlContent", htmlContent
        );

        restClient.post()
                .uri("/v3/smtp/email")
                .header("api-key", apiKey)
                .body(payload)
                .retrieve()
                .toBodilessEntity();
    }


    @Async    
    public void sendOtp(String toEmail, String otp){
       sendEmail(
    toEmail,
    "CareBridge OTP Verification",
        EmailMessages.otpTemplate(otp));
    }

      @Async
public void donorProfileNotification(
        String toEmail,
        String donorName) {

    sendEmail(
            toEmail,
            "Thank You for Joining CareBridge",
            EmailMessages.donorProfilePendingTemplate(donorName)
    );
}

   @Async
public void orpProfileNotification(
        String toEmail,
        String orpAdminName){

    sendEmail(
            toEmail,
            "Thank You for Registering Your Orphanage with CareBridge",
            EmailMessages.orphanageProfilePendingTemplate(
                    orpAdminName)
    );
}


   @Async
public void donorApproveNotification(
        String toEmail,
        String donorName,
        String donorId){

    sendEmail(
            toEmail,
            "Your CareBridge Donor Profile Has Been Approved",
            EmailMessages.donorApprovedTemplate(
                    donorName,
                    donorId)
    );
}

  @Async
public void donorRejectionNotification(
        String toEmail,
        String reason,
        String donorName) {

    sendEmail(
            toEmail,
            "Update Regarding Your CareBridge Donor Profile",
            EmailMessages.donorRejectedTemplate(
                    donorName,
                    reason)
    );
}

      @Async
public void approveOrpNotification(
        String toEmail,
        String orpAdminName,
        String orpName,
        String orpId) {

    sendEmail(
            toEmail,
            "Your Orphanage Profile Has Been Approved",
            EmailMessages.orphanageApprovedTemplate(
                    orpAdminName,
                    orpName,
                    orpId)
    );
}
      @Async
public void rejectOrpNotification(
        String toEmail,
        String orpAdminName,
        String orpName,
        String reason) {

    sendEmail(
            toEmail,
            "Update Regarding Your Orphanage Profile",
            EmailMessages.orphanageRejectedTemplate(
                    orpAdminName,
                    orpName,
                    reason)
    );
}

@Async
public void donationInitiated(String toEmail, String donorId, String donorName, String donorPhone, String modeOfDonation, String donationId, String needItemId, String needItemName, String expectedDonationDate, String expectedDeliveryDate, String remainingQuantity, String donorDonationQuantity, String updatedRemainingQuantity) {

    sendEmail(
            toEmail,
            "New Donation Request Initiated - CareBridge",
            EmailMessages.donationInitiatedTemplate(
                    donorId,
                    donorName,
                    donorPhone,
                    modeOfDonation,
                    donationId,
                    needItemId,
                    needItemName,
                    expectedDonationDate,
                    expectedDeliveryDate,
                    remainingQuantity,
                    donorDonationQuantity,
                    updatedRemainingQuantity
            )
    );
}

   @Async
public void donationCancelNotification(
        String toEmail,
        String donationId,
        String donorId,
        String donorQuantity,
        String updatedRemainingQuantity) {

    sendEmail(
            toEmail,
            "Donation Request Cancelled - CareBridge",
            EmailMessages.donationCancelledTemplate(
                    donationId,
                    donorId,
                    donorQuantity,
                    updatedRemainingQuantity
            )
    );
}


@Async
public void donationUpdateNotification(String toEmail,
                                       String donationId,
                                       String donorId,
                                       String donorName,
                                       String needItemId,
                                       String needItemName,
                                       String donationType,
                                       Integer updatedQuantity,
                                       Integer remainingQuantity,
                                       String expectedVisitDate,
                                       String expectedDeliveryDate) {

    sendEmail(
            toEmail,
            "Donation Request Updated - CareBridge",
            EmailMessages.donationUpdatedTemplate(
                    donationId,
                    donorId,
                    donorName,
                    needItemId,
                    needItemName,
                    donationType,
                    updatedQuantity,
                    remainingQuantity,
                    expectedVisitDate,
                    expectedDeliveryDate
            )
    );
}


@Async
public void donationDeliveredNotification(String toEmail, String donationId, String orphanageId, String orphanageName, String donatedQuantity, String itemName, String donationStatus, String note, String deliveredPhotoUrl, String deliveredDate, String donationInitiatedDate) {

    sendEmail(
            toEmail,
            "Your Donation Has Been Successfully Delivered - CareBridge",
            EmailMessages.donationDeliveredTemplate(
                    donationId,
                    orphanageId,
                    orphanageName,
                    donatedQuantity,
                    itemName,
                    donationStatus,
                    note,
                    deliveredPhotoUrl,
                    deliveredDate,
                    donationInitiatedDate
            )
    );
}

@Async
public void bookingNotification(String toEmail, String bookingId, String slotId, Integer numberOfVisitors, Integer availableCapacity, String donorId, String donorName, String donorPhone, String donorMessage, LocalDate slotDate, LocalTime startTime, LocalTime endTime) {

    sendEmail(
            toEmail,
            "New Visit Booking Request - CareBridge",
            EmailMessages.bookingNotificationTemplate(
                    bookingId,
                    slotId,
                    numberOfVisitors,
                    availableCapacity,
                    donorId,
                    donorName,
                    donorPhone,
                    donorMessage,
                    slotDate,
                    startTime,
                    endTime
            )
    );
}


@Async
public void bookingRejectedMail(
        String toEmail,
        String donorName,
        String bookingId,
        String rejectionReason) {

    sendEmail(
            toEmail,
            "Your Visit Booking Has Been Rejected - CareBridge",
            EmailMessages.bookingRejectedTemplate(
                    donorName,
                    bookingId,
                    rejectionReason
            )
    );
}


@Async
public void bookingConfirmationMail(String toEmail, String donorName, String bookingId, LocalDate visitDate, LocalTime startTime, LocalTime endTime, Integer numberOfVisitors, VisitBookingStatus bookingStatus, String orphanageName, String district, String state, String websiteLink, String socialMediaLinks) {

    sendEmail(
            toEmail,
            "Your Visit Booking Has Been Confirmed - CareBridge",
            EmailMessages.bookingConfirmedTemplate(
                    donorName,
                    bookingId,
                    visitDate,
                    startTime,
                    endTime,
                    numberOfVisitors,
                    bookingStatus,
                    orphanageName,
                    district,
                    state,
                    websiteLink,
                    socialMediaLinks
            )
    );
}

@Async
public void visitCompletedMail(String Email,String donorName, String status, String bookingId, String date,String orpname){
        sendEmail(Email, "Your Visit Has Been Completed", EmailMessages.visitCompletedTemplate(donorName, status, bookingId, date, orpname));
}

}
