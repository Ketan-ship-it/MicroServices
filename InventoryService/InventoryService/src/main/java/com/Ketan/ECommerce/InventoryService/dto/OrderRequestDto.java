package com.Ketan.ECommerce.InventoryService.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private Double totalPrice;
    private List<OrderItemDto> orderItems;
}
