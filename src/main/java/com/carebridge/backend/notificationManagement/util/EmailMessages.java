package com.carebridge.backend.notificationManagement.util;

import java.time.LocalDate;
import java.time.LocalTime;

import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;

public class EmailMessages {
    
   

    public static String otpTemplate(String otp) {

        return """
        <div style="
            max-width:600px;
            margin:auto;
            font-family:Arial,sans-serif;
            background:#ffffff;
            border:1px solid #e5e7eb;
            border-radius:12px;
            overflow:hidden;
        ">

            <div style="
                background:#15803d;
                padding:20px;
                text-align:center;
                color:white;
            ">
                <h1 style="margin:0;">CareBridge</h1>
                <p style="margin:5px 0 0 0;">
                    Bridging Hearts, Building Futures
                </p>
            </div>

            <div style="padding:30px;">

                <h2 style="color:#111827;">
                    OTP Verification
                </h2>

                <p style="color:#4b5563;">
                    Thank you for using CareBridge.
                    Please use the OTP below to continue.
                </p>

                <div style="
                    text-align:center;
                    margin:30px 0;
                ">
                    <span style="
                        display:inline-block;
                        background:#f0fdf4;
                        border:2px solid #22c55e;
                        color:#15803d;
                        padding:18px 35px;
                        border-radius:10px;
                        font-size:32px;
                        font-weight:bold;
                        letter-spacing:8px;
                    ">
                        %s
                    </span>
                </div>

                <p style="
                    color:#6b7280;
                    text-align:center;
                ">
                    Valid for 5 minutes
                </p>

            </div>

            <div style="
                background:#f9fafb;
                text-align:center;
                padding:15px;
                color:#6b7280;
                font-size:12px;
            ">
                © 2026 CareBridge • Connecting Donors & Orphanages
            </div>

        </div>
        """.formatted(otp);
    }

    public static String donorProfilePendingTemplate(String donorName) {

    return """
    <div style="
        max-width:600px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:#ffffff;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#15803d;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                Bridging Hearts, Building Futures
            </p>
        </div>

        <div style="padding:30px;">

            <h2 style="color:#111827;">
                Thank You for Joining CareBridge
            </h2>

            <p style="color:#374151;">
                Dear <strong>%s</strong>,
            </p>

            <p style="color:#4b5563;">
                Thank you for completing your donor profile on CareBridge.
                We truly appreciate your willingness to support and make a
                positive impact in the lives of children and orphanages.
            </p>

            <p style="color:#4b5563;">
                Your profile has been successfully submitted and is currently
                under verification by our team.
            </p>

            <div style="
                background:#fef3c7;
                color:#92400e;
                padding:15px;
                border-radius:8px;
                margin:20px 0;
                text-align:center;
                font-weight:bold;
            ">
                Profile Status: Pending Verification
            </div>

            <p style="color:#4b5563;">
                Once the verification process is completed, you will receive
                another update regarding your approval status.
            </p>

            <p style="color:#4b5563;">
                We are grateful to have compassionate people like you as part
                of the CareBridge community.
            </p>

            <p style="margin-top:30px;">
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(donorName);
}


public static String orphanageProfilePendingTemplate(
        String orphanageAdminName) {

    return """
    <div style="
        max-width:600px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:#ffffff;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#15803d;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                Bridging Hearts, Building Futures
            </p>
        </div>

        <div style="padding:30px;">

            <h2 style="color:#111827;">
                Orphanage Registration Received
            </h2>

            <p style="color:#374151;">
                Dear <strong>%s</strong>,
            </p>

            <p style="color:#4b5563;">
                Greetings from CareBridge.
            </p>

            <p style="color:#4b5563;">
                Thank you for successfully completing your orphanage profile
                registration on CareBridge. We sincerely appreciate your
                commitment to supporting and caring for children in need.
            </p>

            <p style="color:#4b5563;">
                Your orphanage profile has been submitted successfully and is
                currently being reviewed by our verification team.
            </p>

            <div style="
                background:#fef3c7;
                color:#92400e;
                padding:15px;
                border-radius:8px;
                margin:20px 0;
                text-align:center;
                font-weight:bold;
            ">
                Profile Status : Pending Verification
            </div>

            <p style="color:#4b5563;">
                Once verification is completed, you will receive another
                notification regarding approval status and onboarding steps.
            </p>

            <p style="color:#4b5563;">
                We are honored to welcome your orphanage into the CareBridge
                community. Together, we can build a transparent, trusted,
                and impactful support network for children and donors.
            </p>

            <p style="margin-top:30px;">
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(orphanageAdminName);
}


public static String donorApprovedTemplate(
        String donorName,
        String donorId) {

    return """
    <div style="
        max-width:600px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:#ffffff;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#15803d;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                Bridging Hearts, Building Futures
            </p>
        </div>

        <div style="padding:30px;">

            <h2 style="
                color:#15803d;
                text-align:center;
            ">
                🎉 Profile Approved
            </h2>

            <p style="color:#374151;">
                Dear <strong>%s</strong>,
            </p>

            <p style="color:#4b5563;">
                We are delighted to inform you that your donor profile
                has been successfully verified and approved.
            </p>

            <div style="
                background:#dcfce7;
                color:#166534;
                padding:18px;
                border-radius:10px;
                margin:20px 0;
                text-align:center;
                font-weight:bold;
            ">
                Profile Status : APPROVED ✅
            </div>

            <div style="
                background:#f9fafb;
                border:1px solid #d1d5db;
                border-radius:10px;
                padding:15px;
                margin:20px 0;
            ">
                <p style="margin:0;color:#374151;">
                    <strong>Your CareBridge ID</strong>
                </p>

                <h2 style="
                    margin-top:10px;
                    color:#15803d;
                    letter-spacing:2px;
                ">
                    %s
                </h2>
            </div>

            <p style="color:#4b5563;">
                Thank you for your patience and for choosing to be a
                part of the CareBridge community.
            </p>

            <p style="color:#4b5563;">
                You can now access donor features and continue your
                journey of creating positive impact through CareBridge.
            </p>

            <p style="color:#4b5563;">
                We deeply value your trust and support and look
                forward to building a compassionate community together.
            </p>

            <p style="margin-top:30px;">
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(donorName, donorId);
}


public static String donorRejectedTemplate(
        String donorName,
        String reason) {

    return """
    <div style="
        max-width:600px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:#ffffff;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#15803d;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                Bridging Hearts, Building Futures
            </p>
        </div>

        <div style="padding:30px;">

            <h2 style="
                color:#b91c1c;
                text-align:center;
            ">
                Profile Review Update
            </h2>

            <p style="color:#374151;">
                Dear <strong>%s</strong>,
            </p>

            <p style="color:#4b5563;">
                Thank you for showing interest in becoming a donor on
                CareBridge.
            </p>

            <p style="color:#4b5563;">
                After reviewing your profile and submitted details,
                we regret to inform you that your donor profile could
                not be approved at this time.
            </p>

            <div style="
                background:#fef2f2;
                color:#b91c1c;
                padding:15px;
                border-radius:8px;
                margin:20px 0;
                text-align:center;
                font-weight:bold;
            ">
                Profile Status : REJECTED
            </div>

            <div style="
                background:#fff7ed;
                border-left:5px solid #f97316;
                padding:15px;
                margin:20px 0;
                border-radius:6px;
            ">
                <p style="
                    margin:0;
                    color:#9a3412;
                    font-weight:bold;
                ">
                    Reason for Rejection
                </p>

                <p style="
                    margin-top:10px;
                    color:#7c2d12;
                ">
                    %s
                </p>
            </div>

            <p style="color:#4b5563;">
                You may review the reason above, make the necessary
                corrections if applicable, and submit your profile again.
            </p>

            <p style="color:#4b5563;">
                We sincerely appreciate your willingness to support
                orphanages and children in need through CareBridge.
            </p>

            <p style="color:#4b5563;">
                Thank you for your understanding and continued interest.
            </p>

            <p style="margin-top:30px;">
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(donorName, reason);
}


public static String orphanageApprovedTemplate(
        String adminName,
        String orphanageName,
        String orphanageId) {

    return """
    <div style="
        max-width:600px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:#ffffff;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#15803d;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                Bridging Hearts, Building Futures
            </p>
        </div>

        <div style="padding:30px;">

            <h2 style="
                color:#15803d;
                text-align:center;
            ">
                🎉 Orphanage Profile Approved
            </h2>

            <p style="color:#374151;">
                Dear <strong>%s</strong>,
            </p>

            <p style="color:#4b5563;">
                Greetings from CareBridge.
            </p>

            <p style="color:#4b5563;">
                We are pleased to inform you that the orphanage profile for
                <strong>%s</strong> has been successfully verified and approved
                by our team.
            </p>

            <div style="
                background:#dcfce7;
                color:#166534;
                padding:18px;
                border-radius:10px;
                margin:20px 0;
                text-align:center;
                font-weight:bold;
            ">
                Profile Status : APPROVED ✅
            </div>

            <div style="
                background:#f9fafb;
                border:1px solid #d1d5db;
                border-radius:10px;
                padding:15px;
                margin:20px 0;
            ">
                <p style="margin:0;color:#374151;">
                    <strong>Your CareBridge ID</strong>
                </p>

                <h2 style="
                    margin-top:10px;
                    color:#15803d;
                    letter-spacing:2px;
                ">
                    %s
                </h2>
            </div>

            <p style="color:#4b5563;">
                Your orphanage is now officially part of the CareBridge
                community and can start accessing platform services and
                opportunities to connect with donors and supporters.
            </p>

            <p style="color:#4b5563;">
                Thank you for your dedication and commitment toward supporting
                children in need. We are honored to have your organization
                with us in this meaningful journey.
            </p>

            <p style="color:#4b5563;">
                We look forward to creating a positive impact together.
            </p>

            <p style="margin-top:30px;">
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(
            adminName,
            orphanageName,
            orphanageId
    );
}


public static String orphanageRejectedTemplate(
        String adminName,
        String orphanageName,
        String reason) {

    return """
    <div style="
        max-width:600px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:#ffffff;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#15803d;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                Bridging Hearts, Building Futures
            </p>
        </div>

        <div style="padding:30px;">

            <h2 style="
                color:#b91c1c;
                text-align:center;
            ">
                Orphanage Profile Review Update
            </h2>

            <p style="color:#374151;">
                Dear <strong>%s</strong>,
            </p>

            <p style="color:#4b5563;">
                Greetings from CareBridge.
            </p>

            <p style="color:#4b5563;">
                Thank you for registering
                <strong>%s</strong>
                on the CareBridge platform.
            </p>

            <p style="color:#4b5563;">
                After reviewing the submitted details and documents,
                we regret to inform you that your orphanage profile
                could not be approved at this time.
            </p>

            <div style="
                background:#fef2f2;
                color:#b91c1c;
                padding:15px;
                border-radius:8px;
                margin:20px 0;
                text-align:center;
                font-weight:bold;
            ">
                Profile Status : REJECTED
            </div>

            <div style="
                background:#fff7ed;
                border-left:5px solid #f97316;
                padding:15px;
                margin:20px 0;
                border-radius:6px;
            ">
                <p style="
                    margin:0;
                    color:#9a3412;
                    font-weight:bold;
                ">
                    Reason for Rejection
                </p>

                <p style="
                    margin-top:10px;
                    color:#7c2d12;
                ">
                    %s
                </p>
            </div>

            <p style="color:#4b5563;">
                You may review the reason above, make the necessary
                corrections if applicable, and resubmit your profile
                for verification.
            </p>

            <p style="color:#4b5563;">
                We sincerely appreciate your efforts and commitment
                toward supporting children in need.
            </p>

            <p style="color:#4b5563;">
                Thank you for your understanding and interest in
                becoming part of the CareBridge community.
            </p>

            <p style="margin-top:30px;">
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(
            adminName,
            orphanageName,
            reason
    );
}


public static String donationInitiatedTemplate(
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
        <p>
            <strong>Expected Delivery Date:</strong>
            %s
        </p>
        """.formatted(expectedDeliveryDate);

    } else {

        scheduleInfo = """
        <p>
            <strong>Expected Visit Date:</strong>
            %s
        </p>
        """.formatted(expectedDonationDate);
    }

    return """
    <div style="
        max-width:700px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:white;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#15803d;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                New Donation Request Initiated
            </p>
        </div>

        <div style="padding:25px;">

            <div style="
                background:#eff6ff;
                border-left:5px solid #2563eb;
                padding:15px;
                border-radius:6px;
                margin-bottom:20px;
            ">
                A donor has initiated a donation request
                for one of your listed needs.
            </div>

            <h3 style="color:#15803d;">
                Donation Details
            </h3>

            <table style="width:100%%;">
                <tr><td><strong>Donation ID</strong></td><td>%s</td></tr>
                <tr><td><strong>Need Item ID</strong></td><td>%s</td></tr>
                <tr><td><strong>Need Item Name</strong></td><td>%s</td></tr>
            </table>

            <hr>

            <h3 style="color:#15803d;">
                Donor Details
            </h3>

            <table style="width:100%%;">
                <tr><td><strong>Donor ID</strong></td><td>%s</td></tr>
                <tr><td><strong>Donor Name</strong></td><td>%s</td></tr>
                <tr><td><strong>Phone</strong></td><td>%s</td></tr>
            </table>

            <hr>

            <h3 style="color:#15803d;">
                Donation Information
            </h3>

            <table style="width:100%%;">
                <tr><td><strong>Mode</strong></td><td>%s</td></tr>
                <tr><td><strong>Donated Quantity</strong></td><td>%s</td></tr>
                <tr><td><strong>Previous Remaining Qty</strong></td><td>%s</td></tr>
                <tr><td><strong>Updated Remaining Qty</strong></td><td>%s</td></tr>
            </table>

            %s

            <div style="
                margin-top:25px;
                background:#f9fafb;
                padding:15px;
                border-radius:8px;
            ">
                Please review the donation request
                and proceed with the necessary
                coordination through CareBridge.
            </div>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
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
}


public static String donationCancelledTemplate(
        String donationId,
        String donorId,
        String donorQuantity,
        String updatedRemainingQuantity) {

    return """
    <div style="
        max-width:700px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:white;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#ea580c;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                Donation Request Cancelled
            </p>
        </div>

        <div style="padding:25px;">

            <div style="
                background:#fff7ed;
                border-left:5px solid #ea580c;
                padding:15px;
                border-radius:6px;
                margin-bottom:20px;
                color:#9a3412;
            ">
                A donor has cancelled an existing donation request.
            </div>

            <h3 style="color:#ea580c;">
                Cancellation Details
            </h3>

            <table style="
                width:100%;
                border-collapse:collapse;
            ">
                <tr>
                    <td style="padding:8px;">
                        <strong>Donation ID</strong>
                    </td>
                    <td style="padding:8px;">%s</td>
                </tr>

                <tr>
                    <td style="padding:8px;">
                        <strong>Donor ID</strong>
                    </td>
                    <td style="padding:8px;">%s</td>
                </tr>

                <tr>
                    <td style="padding:8px;">
                        <strong>Cancelled Quantity</strong>
                    </td>
                    <td style="padding:8px;">%s</td>
                </tr>

                <tr>
                    <td style="padding:8px;">
                        <strong>Updated Remaining Quantity</strong>
                    </td>
                    <td style="padding:8px;">%s</td>
                </tr>
            </table>

            <div style="
                margin-top:25px;
                background:#f9fafb;
                padding:15px;
                border-radius:8px;
                color:#374151;
            ">
                The required quantity has been updated automatically
                in the CareBridge system.
                Please review the updated need status.
            </div>

            <p style="
                margin-top:25px;
                color:#4b5563;
            ">
                Thank you for your continued support and cooperation.
            </p>

            <p style="margin-top:30px;">
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(
            donationId,
            donorId,
            donorQuantity,
            updatedRemainingQuantity
    );
}


    public static String donationUpdatedTemplate(
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
        <tr>
            <td style="padding:8px;">
                <strong>Expected Delivery Date</strong>
            </td>
            <td style="padding:8px;">%s</td>
        </tr>
        """.formatted(expectedDeliveryDate);

    } else {

        scheduleInfo = """
        <tr>
            <td style="padding:8px;">
                <strong>Expected Visit Date</strong>
            </td>
            <td style="padding:8px;">%s</td>
        </tr>
        """.formatted(expectedVisitDate);
    }

    return """
    <div style="
        max-width:700px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:white;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#2563eb;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                Donation Request Updated
            </p>
        </div>

        <div style="padding:25px;">

            <div style="
                background:#eff6ff;
                border-left:5px solid #2563eb;
                padding:15px;
                border-radius:6px;
                margin-bottom:20px;
                color:#1e40af;
            ">
                A donor has updated an existing donation request.
            </div>

            <h3 style="color:#2563eb;">
                Donation Details
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Donation ID</strong></td>
                    <td>%s</td>
                </tr>
                <tr>
                    <td><strong>Need Item ID</strong></td>
                    <td>%s</td>
                </tr>
                <tr>
                    <td><strong>Need Item Name</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#2563eb;">
                Donor Details
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Donor ID</strong></td>
                    <td>%s</td>
                </tr>
                <tr>
                    <td><strong>Donor Name</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#2563eb;">
                Updated Donation Information
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Donation Type</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Updated Quantity</strong></td>
                    <td>%d</td>
                </tr>

                <tr>
                    <td><strong>Remaining Quantity</strong></td>
                    <td>%d</td>
                </tr>

                %s

            </table>

            <div style="
                margin-top:25px;
                background:#f9fafb;
                padding:15px;
                border-radius:8px;
            ">
                Please review the updated donation details
                on the CareBridge platform.
            </div>

            <p style="
                margin-top:20px;
                color:#4b5563;
            ">
                Thank you for your continued support and cooperation.
            </p>

            <p style="margin-top:30px;">
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
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
}

    public static String donationDeliveredTemplate(
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

    String orphanageNote =
            note != null && !note.isBlank()
                    ? note
                    : "No additional message provided.";

    String imageSection =
            deliveredPhotoUrl != null && !deliveredPhotoUrl.isBlank()
                    ? """
                    <div style="text-align:center;margin-top:20px;">
                        <img src="%s"
                             style="
                                max-width:100%%;
                                border-radius:10px;
                                border:1px solid #e5e7eb;
                             ">
                    </div>
                    """.formatted(deliveredPhotoUrl)
                    : "";

    return """
    <div style="
        max-width:700px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:white;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#15803d;
            color:white;
            padding:25px;
            text-align:center;
        ">
            <h1 style="margin:0;">
                🎉 Donation Successfully Delivered
            </h1>

            <p style="margin-top:10px;">
                Thank you for making a difference
            </p>
        </div>

        <div style="padding:30px;">

            <p>
                Dear Donor,
            </p>

            <p>
                We are delighted to inform you that your donation
                has been successfully delivered.
            </p>

            <div style="
                background:#dcfce7;
                color:#166534;
                padding:18px;
                border-radius:10px;
                margin:20px 0;
                text-align:center;
                font-weight:bold;
            ">
                Donation Status : %s ✅
            </div>

            <h3 style="color:#15803d;">
                Donation Details
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Donation ID</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Item Name</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Quantity</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#15803d;">
                Receiving Orphanage
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Orphanage ID</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Name</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#15803d;">
                Donation Timeline
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Initiated Date</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Delivered Date</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#15803d;">
                Message From Orphanage
            </h3>

            <div style="
                background:#f9fafb;
                padding:15px;
                border-radius:8px;
                border-left:4px solid #15803d;
            ">
                %s
            </div>

            %s

            <div style="
                margin-top:30px;
                background:#eff6ff;
                padding:20px;
                border-radius:10px;
                text-align:center;
            ">
                ❤️ Your generosity helps create
                meaningful change in the lives
                of children in need.
            </div>

            <p style="margin-top:25px;">
                We sincerely appreciate your support
                and being part of the CareBridge community.
            </p>

            <p>
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(
            donationStatus,
            donationId,
            itemName,
            donatedQuantity,
            orphanageId,
            orphanageName,
            donationInitiatedDate,
            deliveredDate,
            orphanageNote,
            imageSection
    );
}


public static String bookingNotificationTemplate(
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

    String message = donorMessage != null && !donorMessage.isBlank()
            ? donorMessage
            : "No message provided.";

    return """
    <div style="
        max-width:700px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:white;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#7c3aed;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                📅 New Visit Booking Request
            </p>
        </div>

        <div style="padding:25px;">

            <div style="
                background:#f3e8ff;
                border-left:5px solid #7c3aed;
                padding:15px;
                border-radius:6px;
                margin-bottom:20px;
                color:#5b21b6;
            ">
                A donor has successfully booked a visit slot.
            </div>

            <h3 style="color:#7c3aed;">
                Booking Details
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Booking ID</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Slot ID</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Visitors</strong></td>
                    <td>%d</td>
                </tr>

                <tr>
                    <td><strong>Remaining Capacity</strong></td>
                    <td>%d</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#7c3aed;">
                Donor Details
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Donor ID</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Donor Name</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Phone</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#7c3aed;">
                Visit Schedule
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Visit Date</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Start Time</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>End Time</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#7c3aed;">
                Donor Message
            </h3>

            <div style="
                background:#f9fafb;
                border-left:4px solid #7c3aed;
                padding:15px;
                border-radius:8px;
            ">
                %s
            </div>

            <div style="
                margin-top:25px;
                background:#f9fafb;
                padding:15px;
                border-radius:8px;
            ">
                Please review the booking details and
                coordinate further if required.
            </div>

            <p style="margin-top:25px;">
                Thank you for being part of the CareBridge community.
            </p>

            <p>
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
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
            message
    );
}


public static String bookingRejectedTemplate(
        String donorName,
        String bookingId,
        String rejectionReason) {

    String reason =
            rejectionReason != null && !rejectionReason.isBlank()
                    ? rejectionReason
                    : "No specific reason provided.";

    return """
    <div style="
        max-width:650px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:white;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#b91c1c;
            color:white;
            padding:20px;
            text-align:center;
        ">
            <h1 style="margin:0;">CareBridge</h1>
            <p style="margin-top:8px;">
                Visit Booking Request Rejected
            </p>
        </div>

        <div style="padding:30px;">

            <p>
                Dear <strong>%s</strong>,
            </p>

            <p>
                Greetings from CareBridge.
            </p>

            <p>
                We regret to inform you that your
                orphanage visit booking request
                could not be approved at this time.
            </p>

            <div style="
                background:#fef2f2;
                color:#b91c1c;
                padding:18px;
                border-radius:10px;
                margin:20px 0;
                text-align:center;
                font-weight:bold;
            ">
                Booking Status : REJECTED ❌
            </div>

            <h3 style="color:#b91c1c;">
                Booking Details
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Booking ID</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#b91c1c;">
                Reason For Rejection
            </h3>

            <div style="
                background:#fff7ed;
                border-left:5px solid #f97316;
                padding:15px;
                border-radius:8px;
                color:#9a3412;
            ">
                %s
            </div>

            <div style="
                margin-top:25px;
                background:#f9fafb;
                padding:15px;
                border-radius:8px;
            ">
                You may review the reason provided
                and book another available slot
                if applicable.
            </div>

            <p style="margin-top:25px;">
                Thank you for your understanding
                and continued support toward
                helping children in need.
            </p>

            <p>
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(
            donorName,
            bookingId,
            reason
    );
}
public static String bookingConfirmedTemplate(
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

    String website =
            (websiteLink != null && !websiteLink.isBlank())
                    ? websiteLink
                    : "Not Available";

    String socialLinks =
            (socialMediaLinks != null && !socialMediaLinks.isBlank())
                    ? socialMediaLinks
                    : "No social media links available";

    return """
    <div style="
        max-width:700px;
        margin:auto;
        font-family:Arial,sans-serif;
        background:white;
        border:1px solid #e5e7eb;
        border-radius:12px;
        overflow:hidden;
    ">

        <div style="
            background:#15803d;
            color:white;
            padding:25px;
            text-align:center;
        ">
            <h1 style="margin:0;">
                🎉 Visit Booking Confirmed
            </h1>

            <p style="margin-top:10px;">
                Your visit has been successfully scheduled
            </p>
        </div>

        <div style="padding:30px;">

            <p>
                Dear <strong>%s</strong>,
            </p>

            <p>
                We are pleased to inform you that your
                orphanage visit booking has been confirmed.
            </p>

            <div style="
                background:#dcfce7;
                color:#166534;
                padding:18px;
                border-radius:10px;
                margin:20px 0;
                text-align:center;
                font-weight:bold;
            ">
                Booking Status : %s ✅
            </div>

            <h3 style="color:#15803d;">
                Booking Details
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Booking ID</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Visitors</strong></td>
                    <td>%d</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#15803d;">
                Visit Schedule
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Date</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Start Time</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>End Time</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#15803d;">
                Orphanage Details
            </h3>

            <table style="width:100%%;">
                <tr>
                    <td><strong>Name</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>District</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>State</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <hr>

            <h3 style="color:#15803d;">
                Website
            </h3>

            <div style="
                background:#f9fafb;
                padding:12px;
                border-radius:8px;
                margin-bottom:15px;
            ">
                %s
            </div>

            <h3 style="color:#15803d;">
                Social Media
            </h3>

            <div style="
                background:#f9fafb;
                padding:12px;
                border-radius:8px;
            ">
                %s
            </div>

            <div style="
                margin-top:25px;
                background:#eff6ff;
                padding:18px;
                border-radius:10px;
                text-align:center;
            ">
                Please arrive on time and coordinate
                responsibly during your visit.
            </div>

            <p style="margin-top:25px;">
                Thank you for your kindness and support
                toward children in need.
            </p>

            <p>
                Warm Regards,<br>
                <strong>Team CareBridge</strong>
            </p>

        </div>

        <div style="
            background:#f9fafb;
            text-align:center;
            padding:15px;
            color:#6b7280;
            font-size:12px;
        ">
            © 2026 CareBridge • Connecting Donors & Orphanages
        </div>

    </div>
    """.formatted(
            donorName,
            bookingStatus,
            bookingId,
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
}

public static String visitCompletedTemplate(
        String donorName,
        String status,
        String bookingId,
        String visitDate,
        String orphanageName
){

    return """
        <html>
        <body style="font-family: Arial, sans-serif; line-height:1.6; color:#333;">
        
            <h2 style="color:#2E8B57;">
                Visit Status Update
            </h2>

            <p>Dear %s,</p>

            <p>
                We are pleased to inform you that your visit booking has been
                <strong>%s</strong>.
            </p>

            <table style="border-collapse: collapse;">
                <tr>
                    <td><strong>Booking ID:</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Visit Date:</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Orphanage:</strong></td>
                    <td>%s</td>
                </tr>

                <tr>
                    <td><strong>Status:</strong></td>
                    <td>%s</td>
                </tr>
            </table>

            <br>

            <p>
                Thank you for taking the time to visit and support the children.
                Your contribution and presence help create a positive impact in
                their lives.
            </p>

            <p>
                We appreciate your support and look forward to your continued
                association with CareBridge.
            </p>

            <br>

            <p>
                Regards,<br>
                <strong>CareBridge Team</strong>
            </p>

        </body>
        </html>
        """
        .formatted(
                donorName,
                status,
                bookingId,
                visitDate,
                orphanageName,
                status
        );
}

}

