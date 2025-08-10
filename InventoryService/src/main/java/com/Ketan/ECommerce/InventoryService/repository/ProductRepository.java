package com.Ketan.ECommerce.InventoryService.repository;

import com.Ketan.ECommerce.InventoryService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
