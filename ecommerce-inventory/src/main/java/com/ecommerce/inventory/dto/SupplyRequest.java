package com.ecommerce.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplyRequest {
    private String skuCode;
    private String name;
    private int quantity;
}
