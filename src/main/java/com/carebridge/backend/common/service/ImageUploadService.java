package com.carebridge.backend.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file){

        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());

            return uploadResult.get("secure_url")
            .toString();
        }
        catch(Exception e){
            throw new RuntimeException(
                    "Failed to upload image"
            );
        }
    }
    
}
