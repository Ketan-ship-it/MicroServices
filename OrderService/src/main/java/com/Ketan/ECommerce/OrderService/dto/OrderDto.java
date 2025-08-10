package com.Ketan.ECommerce.OrderService.dto;

import com.Ketan.ECommerce.OrderService.entities.OrderItem;
import com.Ketan.ECommerce.OrderService.entities.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private OrderStatus orderStatus;
    private Double totalPrice;
    private List<OrderItem> orderItems;
}
