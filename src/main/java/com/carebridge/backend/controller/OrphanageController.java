package com.carebridge.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrphanageController {
    

    @GetMapping("/orphanage/test")
    public String orptest(){
        return "orp api accessed";
    }
}
