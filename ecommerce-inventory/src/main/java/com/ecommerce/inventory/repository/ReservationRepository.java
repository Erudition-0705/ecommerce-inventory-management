package com.ecommerce.inventory.repository;

import com.ecommerce.inventory.entity.Reservation;
import com.ecommerce.inventory.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByItemAndStatus(Item item, Reservation.Status status);
}
