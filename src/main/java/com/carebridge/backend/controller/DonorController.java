package com.carebridge.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DonorController {
     @GetMapping("/donor/test")
    public String donorTest() {
        return "Donor API accessed";
    }
}
