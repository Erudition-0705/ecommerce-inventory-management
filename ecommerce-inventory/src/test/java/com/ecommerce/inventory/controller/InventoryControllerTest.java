package com.ecommerce.inventory.controller;

import com.ecommerce.inventory.dto.*;
import com.ecommerce.inventory.entity.Item;
import com.ecommerce.inventory.entity.Reservation;
import com.ecommerce.inventory.service.InventoryService;
import com.ecommerce.inventory.service.ItemService;
import com.ecommerce.inventory.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InventoryControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private InventoryController inventoryController;

    public InventoryControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSupplyItem() {
        SupplyRequest request = new SupplyRequest("SKU123", "Phone", 10);
        Item item = new Item(1L, "SKU123", "Phone", "desc", 10000.0);

        when(itemService.createOrGetItem(any(), any())).thenReturn(item);

        ResponseEntity<String> response = inventoryController.supplyItem(request);
        verify(inventoryService).supplyItem(item, 10);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testReserveItem() {
        ReservationRequest request = new ReservationRequest("SKU123", 5, "user123");

        Item item = new Item(1L, "SKU123", "Phone", "desc", 10000.0);
        Reservation reservation = Reservation.builder()
                .id(1L)
                .quantity(5)
                .item(item)
                .reservedBy("user123")
                .build();

        when(itemService.getItemBySkuCode("SKU123")).thenReturn(Optional.of(item));
        when(reservationService.reserveItem(item, 5, "user123")).thenReturn(reservation);

        ResponseEntity<ReservationResponse> response = inventoryController.reserveItem(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Reservation successful", response.getBody().getMessage());
        assertEquals(1L, response.getBody().getReservationId());
    }


    @Test
    void testCancelReservation() {
        CancelRequest request = new CancelRequest(1L);

        ResponseEntity<String> response = inventoryController.cancelReservation(request);
        verify(reservationService).cancelReservation(1L);
        assertEquals("Reservation with ID 1 cancelled", response.getBody());
    }

    @Test
    void testCheckAvailability() {
        String sku = "SKU123";
        Item item = new Item(1L, sku, "Phone", "desc", 10000.0);

        when(itemService.getItemBySkuCode(sku)).thenReturn(Optional.of(item));
        when(inventoryService.getAvailableQuantity(item)).thenReturn(15);

        ResponseEntity<AvailabilityResponse> response = inventoryController.checkAvailability(sku);
        assertEquals(15, response.getBody().getAvailableQuantity());
    }
}
