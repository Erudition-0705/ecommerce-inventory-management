package com.ecommerce.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.entity.Item;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByItem(Item item);
}
