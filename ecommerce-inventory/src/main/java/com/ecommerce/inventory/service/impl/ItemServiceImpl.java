package com.ecommerce.inventory.service.impl;

import com.ecommerce.inventory.entity.Item;
import com.ecommerce.inventory.exception.ItemNotFoundException;
import com.ecommerce.inventory.repository.ItemRepository;
import com.ecommerce.inventory.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item createOrGetItem(String skuCode, String name) {
        return itemRepository.findBySkuCode(skuCode)
//                .orElseThrow(() -> new ItemNotFoundException("Item not found: " + skuCode));
        		.orElseGet(() -> {
                    Item newItem = new Item();
                    newItem.setSkuCode(skuCode);
                    newItem.setName(name);
                    return itemRepository.save(newItem);
                });
        
                

    }

    @Override
    public Optional<Item> getItemBySkuCode(String skuCode) {
        return itemRepository.findBySkuCode(skuCode);
    }
}
