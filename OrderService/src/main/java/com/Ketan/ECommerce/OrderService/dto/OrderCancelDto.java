package com.Ketan.ECommerce.OrderService.dto;

import com.Ketan.ECommerce.OrderService.entities.enums.cancelReason;
import lombok.Data;

@Data
public class OrderCancelDto {
    private Long order_id;
    private cancelReason reasonForCancellation;
    private String issue;
}
