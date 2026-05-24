package com.carebridge.backend.needsManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.needsManagement.dto.NeedItemRequest;
import com.carebridge.backend.needsManagement.dto.NeedItemResponse;
import com.carebridge.backend.needsManagement.entity.NeedItem;
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

    // @PostMapping("/test")
    // public String hello(){
    //     return "hello";
    // }

    @PutMapping("/updateItem/{id}")
    public ResponseEntity<NeedItemResponse> updateItem(@RequestBody NeedItemRequest request, @PathVariable String id){

        NeedItemResponse response = needItemService.updateNeedItem(request,id);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<NeedItemResponse> deleteItem(@PathVariable String id){
        NeedItemResponse response = needItemService.deleteNeedItem(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activeNeeds")
    public ResponseEntity<List<NeedItem>> getAllActiveNeeds(){
        List<NeedItem> items = needItemService.getAllActiveNeeds();
        return ResponseEntity.ok(items);
    }
}
