package com.Ketan.ECommerce.ShippingService.repository;

import com.Ketan.ECommerce.ShippingService.entities.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingOrderRepository extends JpaRepository<ShippingOrder,Long> {
}
