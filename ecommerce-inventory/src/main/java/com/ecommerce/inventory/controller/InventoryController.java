package com.ecommerce.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.inventory.dto.*;
import com.ecommerce.inventory.entity.Item;
import com.ecommerce.inventory.entity.Reservation;
import com.ecommerce.inventory.service.InventoryService;
import com.ecommerce.inventory.service.ItemService;
import com.ecommerce.inventory.service.ReservationService;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class InventoryController {

    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final ReservationService reservationService;

    @PostMapping("/supply")
    public ResponseEntity<String> supplyItem(@RequestBody SupplyRequest request) {
        Item item = itemService.createOrGetItem(request.getSkuCode(), request.getName());
        inventoryService.supplyItem(item, request.getQuantity());
        return ResponseEntity.ok("Supplied " + request.getQuantity() + " units for item " + item.getSkuCode());
    }

    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponse> reserveItem(@RequestBody ReservationRequest request) {
        Item item = itemService.getItemBySkuCode(request.getSkuCode())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        Reservation reservation = reservationService.reserveItem(item, request.getQuantity(),request.getReservedBy());
        return ResponseEntity.ok(new ReservationResponse(reservation.getId(), "Reservation successful"));
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@RequestBody CancelRequest request) {
        reservationService.cancelReservation(request.getReservationId());
        return ResponseEntity.ok("Reservation with ID " + request.getReservationId() + " cancelled");
    }

    @GetMapping("/{skuCode}/availability")
    public ResponseEntity<AvailabilityResponse> checkAvailability(@PathVariable("skuCode") String skuCode) {
        Item item = itemService.getItemBySkuCode(skuCode)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        int available = inventoryService.getAvailableQuantity(item);
        return ResponseEntity.ok(new AvailabilityResponse(skuCode, available));
    }
}
