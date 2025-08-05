package com.Ketan.ECommerce.OrderService.clients;

import com.Ketan.ECommerce.OrderService.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shipping-service" , path = "/shipping")
public interface ShippingFeignClient {

    @PostMapping("/core/incomingDelivery")
    String incomingDeliveryOrders(@RequestBody OrderDto orderDto);

}
