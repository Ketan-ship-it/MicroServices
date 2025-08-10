package com.Ketan.ECommerce.OrderService.clients;

import com.Ketan.ECommerce.OrderService.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service" , path = "/inventory")
public interface InventoryFeignClient {

    @PutMapping("/products/reduceStocks")
    Double reduceStocks(@RequestBody OrderDto orderDto);

    @PutMapping("/products/addStocks")
    Double addStocks(@RequestBody OrderDto orderDto);

}
