package com.Ketan.ECommerce.ShippingService.client.client;

import com.Ketan.ECommerce.ShippingService.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "order-service" , path = "/orders")
public interface OrderFeignClient {

    @GetMapping("/core/id/{id}")
    Optional<OrderDto> getOrderDto(@PathVariable Long id);

}
