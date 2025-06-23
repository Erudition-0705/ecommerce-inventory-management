package com.ecommerce.inventory.service.impl;

import com.ecommerce.inventory.entity.Item;
import com.ecommerce.inventory.entity.Reservation;
import com.ecommerce.inventory.entity.Reservation.Status;
import com.ecommerce.inventory.repository.ReservationRepository;
import com.ecommerce.inventory.repository.InventoryRepository;
import com.ecommerce.inventory.entity.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryServiceImpl inventoryService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReserveItem_Success() {
        Item item = new Item(1L, "SKU123", "Phone", "desc", 10000.0);
        Inventory inventory = new Inventory(1L, item, 10);

        when(inventoryRepository.findByItem(item)).thenReturn(Optional.of(inventory));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

        Reservation reservation = reservationService.reserveItem(item, 5,"user");

        assertNotNull(reservation);
        assertEquals(5, reservation.getQuantity());
        assertEquals(Status.RESERVED, reservation.getStatus());
    }

    @Test
    void testCancelReservation() {
        Item item = new Item(1L, "SKU123", "Phone", "desc", 10000.0);
        Inventory inventory = Inventory.builder().item(item).availableQuantity(5).build();

        Reservation reservation = Reservation.builder()
                .id(1L)
                .item(item)
                .quantity(3)
                .status(Reservation.Status.RESERVED)
                .build();

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(inventoryService.supplyItem(any(Item.class), anyInt())).thenReturn(inventory);

        reservationService.cancelReservation(1L);

        verify(reservationRepository, times(1)).save(reservation);
        assertEquals(Reservation.Status.CANCELLED, reservation.getStatus());
    }
}
