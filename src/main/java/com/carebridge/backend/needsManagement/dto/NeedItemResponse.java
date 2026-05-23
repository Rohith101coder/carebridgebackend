package com.carebridge.backend.needsManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NeedItemResponse {
    
    private String response;

    private String ItemId;
}
