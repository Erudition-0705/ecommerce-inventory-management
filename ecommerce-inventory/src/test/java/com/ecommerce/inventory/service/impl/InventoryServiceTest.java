package com.ecommerce.inventory.service.impl;

import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.entity.Item;
import com.ecommerce.inventory.repository.InventoryRepository;
import com.ecommerce.inventory.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Item item;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        item = new Item(1L, "SKU123", "Phone", "desc", 10000.0);
        inventory = Inventory.builder()
                .id(1L)
                .item(item)
                .availableQuantity(10)
                .build();
    }


    @Test
    void testSupplyItem_Success() {

        when(inventoryRepository.findByItem(item)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(i -> i.getArgument(0));


        Inventory updatedInventory = inventoryService.supplyItem(item, 5);


        assertEquals(15, updatedInventory.getAvailableQuantity());
        verify(inventoryRepository).save(updatedInventory);
    }



    @Test
    void testSupplyItem_NewInventoryCreated() {

        when(inventoryRepository.findByItem(item)).thenReturn(Optional.empty());
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(i -> i.getArgument(0));


        Inventory updatedInventory = inventoryService.supplyItem(item, 7);


        assertEquals(7, updatedInventory.getAvailableQuantity());
        assertEquals(item, updatedInventory.getItem());
        verify(inventoryRepository).save(updatedInventory);
    }


    @Test
    void testReduceAvailableQuantity_Success() {
        when(inventoryRepository.findByItem(item)).thenReturn(Optional.of(inventory));

        inventoryService.reduceAvailableQuantity(item, 5);

        assertEquals(5, inventory.getAvailableQuantity());
        verify(inventoryRepository).save(inventory);
    }

    @Test
    void testReduceAvailableQuantity_InsufficientStock() {
        when(inventoryRepository.findByItem(item)).thenReturn(Optional.of(inventory));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                inventoryService.reduceAvailableQuantity(item, 15)
        );

        assertEquals("Not enough stock available to reserve.", ex.getMessage());
        verify(inventoryRepository, never()).save(any());
    }

    @Test
    void testIncreaseAvailableQuantity_Success() {
        when(inventoryRepository.findByItem(item)).thenReturn(Optional.of(inventory));

        inventoryService.increaseAvailableQuantity(item, 5);

        assertEquals(15, inventory.getAvailableQuantity());
        verify(inventoryRepository).save(inventory);
    }

    @Test
    void testIncreaseAvailableQuantity_InvalidQuantity() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                inventoryService.increaseAvailableQuantity(item, 0)
        );

        assertEquals("Restock quantity must be greater than 0.", ex.getMessage());
        verify(inventoryRepository, never()).save(any());
    }

}