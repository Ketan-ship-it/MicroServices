package com.Ketan.ECommerce.InventoryService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service" , path = "/orders")
public interface OrdersFeignClient {

    @GetMapping(path = "/core/helloOrder")
    String helloOrders();

}
