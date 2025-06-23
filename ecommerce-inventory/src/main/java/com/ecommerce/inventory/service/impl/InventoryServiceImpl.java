package com.ecommerce.inventory.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.entity.Item;
import com.ecommerce.inventory.repository.InventoryRepository;
import com.ecommerce.inventory.service.InventoryService;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    /**
     * Supply stock for a specific item.
     * Adds to the existing available quantity.
     */
    @Override
    @Transactional
    public Inventory supplyItem(Item item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Supply quantity must be greater than 0.");
        }

        Inventory inventory = inventoryRepository.findByItem(item)
                .orElseGet(() -> new Inventory(null, item, 0));

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + quantity);
        return inventoryRepository.save(inventory);
    }

    /**
     * Decrease available quantity (used when item is reserved).
     */
    @Override
    @Transactional
    public void reduceAvailableQuantity(Item item, int quantity) {
        Inventory inventory = getInventory(item);
        int currentAvailable = inventory.getAvailableQuantity();

        if (quantity > currentAvailable) {
            throw new IllegalArgumentException("Not enough stock available to reserve.");
        }

        inventory.setAvailableQuantity(currentAvailable - quantity);
        inventoryRepository.save(inventory);
    }

    /**
     * Increase available quantity (used when reservation is cancelled).
     */
    @Override
    @Transactional
    public void increaseAvailableQuantity(Item item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Restock quantity must be greater than 0.");
        }

        Inventory inventory = getInventory(item);
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + quantity);
        inventoryRepository.save(inventory);
    }

    /**
     * Get current inventory for a given item.
     */
    @Override
    public Inventory getInventory(Item item) {
        return inventoryRepository.findByItem(item)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for item: " + item.getSkuCode()));
    }

    /**
     * Returns how many units are available for the given item.
     */
    @Override
    public int getAvailableQuantity(Item item) {
        return getInventory(item).getAvailableQuantity();
    }
}
