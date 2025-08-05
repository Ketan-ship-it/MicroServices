package com.Ketan.ECommerce.OrderService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private Long id;

    private Long product_id;

    private Integer quantity;
}
