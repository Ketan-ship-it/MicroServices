package com.Ketan.ECommerce.InventoryService.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long product_id;
    private Integer quantity;
}
