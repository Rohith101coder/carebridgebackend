package com.carebridge.backend.needsManagement.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

import com.carebridge.backend.needsManagement.enums.CategoryType;
import com.carebridge.backend.needsManagement.enums.PriorityLevel;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeedItemRequest {

    @NotBlank(message = "Item name is required")
    private String name;

    private String description;

    @NotNull(message = "Category is required")
    private CategoryType category;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Price per quantity is required")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Price must be greater than 0"
    )
    private BigDecimal pricePerQuantity;

    @NotNull(message = "Priority level is required")
    private PriorityLevel priority;

    @NotEmpty(message = "At least one product link is required")
    private List<String> productLinks;
}
