package com.ecommerce.inventory.dto;

import lombok.Data;

@Data
public class ReservationRequest {
    private String skuCode;
    private int quantity;
    private String reservedBy; 
}
