package com.ecommerce.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailabilityResponse {
    private String skuCode;
    private int availableQuantity;
}
