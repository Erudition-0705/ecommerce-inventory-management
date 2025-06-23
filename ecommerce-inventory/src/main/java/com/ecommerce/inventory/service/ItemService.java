package com.ecommerce.inventory.service;

import com.ecommerce.inventory.entity.Item;
import java.util.Optional;

public interface ItemService {
    Item createOrGetItem(String skuCode, String name);
    Optional<Item> getItemBySkuCode(String skuCode);
}
