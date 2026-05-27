package com.carebridge.backend.notificationManagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// import java.time.LocalDate;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;

    @Async    
    public void sendOtp(String toEmail, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("CareBridge OTP Verification");
        message.setText("Your OTP is: "+otp+ "\nValid for 5 minutes.");

        mailSender.send(message);
    }

        @Async
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

    @Async
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


    @Async
    public void donorApproveNotification(String toEmail, String donorName, String donId){
        String subject = "Your CareBridge Donor Profile Has Been Approved";
        String body = """
                Dear %s,

We are delighted to inform you that your donor profile on CareBridge has been successfully verified and approved.

Profile Status: Approved
Your CareBridgeId : %s

Thank you for your patience and for choosing to be a part of the CareBridge community. Your willingness to support orphanages and children in need truly makes a meaningful difference.

You can now access donor features and continue your journey of creating positive impact through CareBridge.

We deeply value your trust and support, and we look forward to building a compassionate community together.

Thank you once again for joining CareBridge.

Warm regards,
Team CareBridge
                """.formatted(donorName, donId);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(toEmail);
                message.setSubject(subject);
                message.setText(body);

                mailSender.send(message);
    }

    @Async
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

        @Async
       public void approveOrpNotification(String toEmail, String orpAdminName, String orpName, String orpId) {

    String subject = "Your Orphanage Profile Has Been Approved";

    String body = """
            Dear %s,

            Greetings from CareBridge.

            We are pleased to inform you that the orphanage profile for "%s" has been successfully verified and approved by our team.

            Profile Status: Approved
            Your CareBridgeId : %s

            Your orphanage is now officially part of the CareBridge community and can start accessing platform services and opportunities to connect with donors and supporters.

            Thank you for your dedication and commitment toward supporting children in need. We are honored to have your organization with us in this meaningful journey.

            We look forward to creating a positive impact together.

            Warm regards,
            Team CareBridge
            """.formatted(orpAdminName, orpName,orpId);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}

        @Async
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


@Async
public void donationInitiated(String toEmail,
                              String donorId,
                              String donorName,
                              String donorPhone,
                              String modeOfDonation,
                              String donationId,
                              String needItemId,
                              String needItemName,
                              String expectedDonationDate,
                              String expectedDeliveryDate,
                              String remainingQuantity,
                              String donorDonationQuantity,
                              String updatedRemainingQuantity) {

    String scheduleInfo;

    if ("ONLINE_ORDER".equalsIgnoreCase(modeOfDonation)) {

        scheduleInfo = """
                Expected Delivery Date : %s
                """.formatted(expectedDeliveryDate);

    } else {

        scheduleInfo = """
                Expected Donation Visit Date : %s
                """.formatted(expectedDonationDate);
    }

    String subject = "New Donation Request Initiated - CareBridge";

    String body = """
            Dear Orphanage Admin,

            Greetings from CareBridge.

            A donor has initiated a donation request for one of your listed needs.

            Donation Details
            -----------------------------------
            Donation ID              : %s
            Need Item ID             : %s
            Need Item Name           : %s

            Donor Details
            -----------------------------------
            Donor ID                 : %s
            Donor Name               : %s
            Donor Phone              : %s

            Donation Information
            -----------------------------------
            Mode of Donation         : %s
            Donated Quantity         : %s
            Previous Remaining Qty   : %s
            Updated Remaining Qty    : %s

            %s

            Please review the donation request and proceed with the necessary coordination through the CareBridge platform.

            Thank you for being part of the CareBridge community and supporting children in need.

            Warm regards,
            Team CareBridge
            """.formatted(
                    donationId,
                    needItemId,
                    needItemName,
                    donorId,
                    donorName,
                    donorPhone,
                    modeOfDonation,
                    donorDonationQuantity,
                    remainingQuantity,
                    updatedRemainingQuantity,
                    scheduleInfo
            );

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}

    @Async
public void donationCancelNotification(String toEmail,
                                       String donationId,
                                       String donorId,
                                       String donorQuantity,
                                       String updatedRemainingQuantity) {

    String subject = "Donation Request Cancelled - CareBridge";

    String body = """
            Dear Orphanage Admin,

            Greetings from CareBridge.

            This is to inform you that a donor has cancelled an existing donation request.

            Cancellation Details
            -----------------------------------
            Donation ID              : %s
            Donor ID                 : %s
            Cancelled Quantity       : %s
            Updated Remaining Qty    : %s

            The required quantity has been updated accordingly in the system.

            Please review the updated need status on the CareBridge platform.

            Thank you for your continued support and cooperation.

            Warm regards,
            Team CareBridge
            """.formatted(
                    donationId,
                    donorId,
                    donorQuantity,
                    updatedRemainingQuantity
            );

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
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

    String scheduleInfo;

    if ("ONLINE_ORDER".equalsIgnoreCase(donationType)) {

        scheduleInfo = """
                Expected Delivery Date : %s
                """.formatted(expectedDeliveryDate);

    } else {

        scheduleInfo = """
                Expected Visit Date    : %s
                """.formatted(expectedVisitDate);
    }

    String subject = "Donation Request Updated - CareBridge";

    String body = """
            Dear Orphanage Admin,

            Greetings from CareBridge.

            A donor has updated an existing donation request associated with one of your listed needs.

            Donation Details
            -----------------------------------
            Donation ID              : %s
            Need Item ID             : %s
            Need Item Name           : %s

            Donor Details
            -----------------------------------
            Donor ID                 : %s
            Donor Name               : %s

            Updated Donation Information
            -----------------------------------
            Donation Type            : %s
            Updated Quantity         : %d
            Remaining Quantity       : %d

            %s

            Please review the updated donation details on the CareBridge platform.

            Thank you for your continued support and cooperation.

            Warm regards,
            Team CareBridge
            """.formatted(
                    donationId,
                    needItemId,
                    needItemName,
                    donorId,
                    donorName,
                    donationType,
                    updatedQuantity,
                    remainingQuantity,
                    scheduleInfo
            );

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}


@Async
public void donationDeliveredNotification(String toEmail,
                                          String donationId,
                                          String orphanageId,
                                          String orphanageName,
                                          String donatedQuantity,
                                          String itemName,
                                          String donationStatus,
                                          String note,
                                          String deliveredPhotoUrl,
                                          String deliveredDate,
                                          String donationInitiatedDate) {

    String subject = "Your Donation Has Been Successfully Delivered - CareBridge";

    String body = """
            Dear Donor,

            Greetings from CareBridge.

            We are happy to inform you that your donation has been successfully delivered to the orphanage.

            Delivery Details
            -----------------------------------
            Donation ID              : %s
            Orphanage ID             : %s
            Orphanage Name           : %s

            Donation Information
            -----------------------------------
            Item Name                : %s
            Donated Quantity         : %s
            Donation Status          : %s

            Timeline
            -----------------------------------
            Donation Initiated Date  : %s
            Delivered Date           : %s

            Message From Orphanage
            -----------------------------------
            %s

            Delivered Item Photo
            -----------------------------------
            %s

            Thank you for your kindness and contribution toward supporting children in need. Your support truly creates a meaningful impact.

            We sincerely appreciate your generosity and being part of the CareBridge community.

            Warm regards,
            Team CareBridge
            """.formatted(
                    donationId,
                    orphanageId,
                    orphanageName,
                    itemName,
                    donatedQuantity,
                    donationStatus,
                    donationInitiatedDate,
                    deliveredDate,
                    note != null && !note.isBlank()
                            ? note
                            : "No additional note provided.",
                    deliveredPhotoUrl
            );

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}


@Async
public void bookingNotification(String toEmail,
                                String bookingId,
                                String slotId,
                                Integer numberOfVisitors,
                                Integer availableCapacity,
                                String donorId,
                                String donorName,
                                String donorPhone,
                                String donorMessage,
                                LocalDate slotDate,
                                LocalTime startTime,
                                LocalTime endTime) {

    String subject = "New Visit Booking Request - CareBridge";

    String body = """
            Dear Orphanage Admin,

            Greetings from CareBridge.

            A donor has successfully booked a visit slot for your orphanage.

            Booking Details
            -----------------------------------
            Booking ID               : %s
            Slot ID                 : %s
            Number Of Visitors      : %d
            Remaining Slot Capacity : %d

            Donor Details
            -----------------------------------
            Donor ID                : %s
            Donor Name              : %s
            Donor Phone             : %s

            Visit Schedule
            -----------------------------------
            Visit Date              : %s
            Start Time              : %s
            End Time                : %s

            Donor Message
            -----------------------------------
            %s

            Please review the booking details in the CareBridge platform and coordinate further if required.

            Thank you for being part of the CareBridge community.

            Warm regards,
            Team CareBridge
            """.formatted(
                    bookingId,
                    slotId,
                    numberOfVisitors,
                    availableCapacity,
                    donorId,
                    donorName,
                    donorPhone,
                    slotDate,
                    startTime,
                    endTime,
                    donorMessage != null && !donorMessage.isBlank()
                            ? donorMessage
                            : "No message provided."
            );

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}


@Async
public void bookingRejectedMail(String toEmail,
                                String donorName,
                                String bookingId,
                                String rejectionReason) {

    String subject = "Your Visit Booking Has Been Rejected - CareBridge";

    String body = """
            Dear %s,

            Greetings from CareBridge.

            We regret to inform you that your orphanage visit booking request could not be approved at this time.

            Booking Details
            -----------------------------------
            Booking ID              : %s
            Booking Status          : REJECTED

            Reason For Rejection
            -----------------------------------
            %s

            You may review the reason provided and book another available slot if applicable.

            Thank you for your understanding and continued support toward helping children in need.

            Warm regards,
            Team CareBridge
            """.formatted(
                    donorName,
                    bookingId,
                    rejectionReason != null && !rejectionReason.isBlank()
                            ? rejectionReason
                            : "No specific reason provided."
            );

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}


@Async
public void bookingConfirmationMail(String toEmail,
                                    String donorName,
                                    String bookingId,
                                    LocalDate visitDate,
                                    LocalTime startTime,
                                    LocalTime endTime,
                                    Integer numberOfVisitors,
                                    VisitBookingStatus bookingStatus,
                                    String orphanageName,
                                    String district,
                                    String state,
                                    String websiteLink,
                                    String socialMediaLinks) {

    String socialLinks = (socialMediaLinks != null && !socialMediaLinks.isEmpty())
            ? String.join(", ", socialMediaLinks)
            : "No social media links available";

    String website = (websiteLink != null && !websiteLink.isBlank())
            ? websiteLink
            : "Not Available";

    String subject = "Your Visit Booking Has Been Confirmed - CareBridge";

    String body = """
            Dear %s,

            Greetings from CareBridge.

            We are pleased to inform you that your orphanage visit booking has been successfully confirmed.

            Booking Details
            -----------------------------------
            Booking ID              : %s
            Booking Status          : %s
            Number Of Visitors      : %d

            Visit Schedule
            -----------------------------------
            Visit Date              : %s
            Start Time              : %s
            End Time                : %s

            Orphanage Details
            -----------------------------------
            Orphanage Name          : %s
            District                : %s
            State                   : %s

            Website
            -----------------------------------
            %s

            Social Media Links
            -----------------------------------
            %s

            Please ensure you arrive on time and coordinate responsibly during your visit.

            Thank you for your kindness and support toward children in need.

            Warm regards,
            Team CareBridge
            """.formatted(
                    donorName,
                    bookingId,
                    bookingStatus,
                    numberOfVisitors,
                    visitDate,
                    startTime,
                    endTime,
                    orphanageName,
                    district,
                    state,
                    website,
                    socialLinks
            );

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
}


}
