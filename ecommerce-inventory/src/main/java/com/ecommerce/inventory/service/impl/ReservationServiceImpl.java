package com.ecommerce.inventory.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ecommerce.inventory.entity.Item;
import com.ecommerce.inventory.entity.Reservation;
import com.ecommerce.inventory.repository.ReservationRepository;
import com.ecommerce.inventory.service.InventoryService;
import com.ecommerce.inventory.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final InventoryService inventoryService;

    /**
     * Reserve an item if available. Decreases available quantity.
     */
    @Override
    @Transactional
    public Reservation reserveItem(Item item, int quantity, String reservedBy) {
//    public Reservation reserveItem(Item item, int quantity) {
        int available = inventoryService.getAvailableQuantity(item);
        if (available < quantity) {
            throw new IllegalArgumentException("Not enough stock to reserve.");
        }

        inventoryService.reduceAvailableQuantity(item, quantity);

        Reservation reservation = Reservation.builder()
                .item(item)
                .quantity(quantity)
                .reservedBy(reservedBy)
                .reservedAt(LocalDateTime.now())
                .status(Reservation.Status.RESERVED)
                .build();

        return reservationRepository.save(reservation);
    }

    /**
     * Cancel an existing reservation. Increases available quantity.
     */
    @Override
    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getStatus() == Reservation.Status.CANCELLED) return;

        inventoryService.increaseAvailableQuantity(reservation.getItem(), reservation.getQuantity());
        reservation.setStatus(Reservation.Status.CANCELLED);
        reservationRepository.save(reservation);
    }

    /**
     * Get all active reservations for an item.
     */
    @Override
    public List<Reservation> getActiveReservations(Item item) {
        return reservationRepository.findByItemAndStatus(item, Reservation.Status.RESERVED);
    }
}
