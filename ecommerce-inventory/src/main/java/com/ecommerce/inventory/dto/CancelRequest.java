package com.ecommerce.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CancelRequest {
    private Long reservationId;
}
