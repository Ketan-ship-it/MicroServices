package com.Ketan.ECommerce.ShippingService.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillDto {
    private ShippingDto shippingDto;
    private OrderDto orderDto;
}
