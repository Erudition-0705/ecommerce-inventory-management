package com.ecommerce.inventory.service;

import java.util.List;

import com.ecommerce.inventory.entity.Item;
import com.ecommerce.inventory.entity.Reservation;

public interface ReservationService {

    Reservation reserveItem(Item item, int quantity, String reservedBy);
//	Reservation reserveItem(Item item, int quantity);

    void cancelReservation(Long reservationId);

    List<Reservation> getActiveReservations(Item item);
}
