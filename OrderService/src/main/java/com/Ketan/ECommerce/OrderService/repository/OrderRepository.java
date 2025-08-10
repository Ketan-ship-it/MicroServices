package com.Ketan.ECommerce.OrderService.repository;

import com.Ketan.ECommerce.OrderService.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
