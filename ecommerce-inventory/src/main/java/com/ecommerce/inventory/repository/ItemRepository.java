package com.ecommerce.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.inventory.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findBySkuCode(String skuCode);
}
