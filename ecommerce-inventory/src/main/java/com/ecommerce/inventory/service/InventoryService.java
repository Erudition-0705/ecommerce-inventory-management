package com.ecommerce.inventory.service;

import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.entity.Item;

public interface InventoryService {
    Inventory supplyItem(Item item, int quantity);
    Inventory getInventory(Item item);
    int getAvailableQuantity(Item item);
//    void updateReservedQuantity(Item item, int delta);
    
    void reduceAvailableQuantity(Item item, int quantity);
    void increaseAvailableQuantity(Item item, int quantity);
}
