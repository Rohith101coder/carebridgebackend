package com.carebridge.backend.needsManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.needsManagement.dto.NeedItemRequest;
import com.carebridge.backend.needsManagement.dto.NeedItemResponse;
import com.carebridge.backend.needsManagement.service.NeedItemService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orphanage/needItem")
public class NeedItemController {
    
    private final NeedItemService needItemService;



    @PostMapping("/create-item")
    public ResponseEntity<NeedItemResponse> createItem(@RequestBody NeedItemRequest request){

        NeedItemResponse response = needItemService.createNeedItem(request);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test")
    public String hello(){
        return "hello";
    }


}
