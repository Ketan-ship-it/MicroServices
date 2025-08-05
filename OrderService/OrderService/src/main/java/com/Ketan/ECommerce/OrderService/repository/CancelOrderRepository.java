package com.Ketan.ECommerce.OrderService.repository;

import com.Ketan.ECommerce.OrderService.entities.CancelledOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelOrderRepository extends JpaRepository<CancelledOrder,Long> {
}
