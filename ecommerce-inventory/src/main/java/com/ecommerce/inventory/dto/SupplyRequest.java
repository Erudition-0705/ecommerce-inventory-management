package com.ecommerce.inventory.dto;

import lombok.Data;

@Data
public class SupplyRequest {
    private String skuCode;
    private String name;
    private int quantity;
}
