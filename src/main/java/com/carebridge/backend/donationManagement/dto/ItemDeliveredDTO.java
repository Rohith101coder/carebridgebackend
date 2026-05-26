package com.carebridge.backend.donationManagement.dto;

import org.springframework.web.multipart.MultipartFile;

// import jakarta.mail.Multipart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDeliveredDTO {
    

    private String note;

    private MultipartFile deliveredPhoto;
}
