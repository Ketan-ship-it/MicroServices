package com.Ketan.ECommerce.ShippingService.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItems;
}
