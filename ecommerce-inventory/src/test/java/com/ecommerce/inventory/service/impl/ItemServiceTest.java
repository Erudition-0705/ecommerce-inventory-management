package com.ecommerce.inventory.service.impl;

import com.ecommerce.inventory.entity.Item;
import com.ecommerce.inventory.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrGetItem_WhenItemExists() {
        String sku = "SKU123";
        Item existingItem = new Item(1L, sku, "Phone", "desc", 10000.0);

        when(itemRepository.findBySkuCode(sku)).thenReturn(Optional.of(existingItem));

        Item result = itemService.createOrGetItem(sku, "Phone");
        assertEquals(existingItem, result);
        verify(itemRepository, never()).save(any());
    }

    @Test
    void testCreateOrGetItem_WhenItemDoesNotExist() {
        String sku = "SKU123";
        when(itemRepository.findBySkuCode(sku)).thenReturn(Optional.empty());

        Item newItem = new Item(null, sku, "Phone", null, null);
        when(itemRepository.save(any(Item.class))).thenReturn(newItem);

        Item result = itemService.createOrGetItem(sku, "Phone");
        assertEquals(sku, result.getSkuCode());
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void testGetItemBySkuCode() {
        String sku = "SKU123";
        Item item = new Item(1L, sku, "Phone", "desc", 10000.0);
        when(itemRepository.findBySkuCode(sku)).thenReturn(Optional.of(item));

        Optional<Item> result = itemService.getItemBySkuCode(sku);
        assertTrue(result.isPresent());
        assertEquals("Phone", result.get().getName());
    }
}
