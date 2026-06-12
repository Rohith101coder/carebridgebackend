package com.carebridge.backend.common.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ImageUploadService {

    private final Cloudinary cloudinary;

    @Async
     @SuppressWarnings("rawtypes")
public CompletableFuture<String> uploadImageAsync(
        MultipartFile file
){

    try{

       
        Map uploadResult =
                cloudinary.uploader().upload(
                        file.getBytes(),
                        Map.of()
                );

        String url =
                uploadResult.get("secure_url")
                        .toString();

        return CompletableFuture.completedFuture(url);

    }catch(Exception e){
        e.printStackTrace();

        throw new RuntimeException(
                "Image upload failed "+e.getMessage(),e
        );
    }
}
    
}
