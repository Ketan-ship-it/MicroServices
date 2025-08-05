package com.Ketan.ECommerce.ShippingService.controller;

import com.Ketan.ECommerce.ShippingService.dto.BillDto;
import com.Ketan.ECommerce.ShippingService.dto.OrderDto;
import com.Ketan.ECommerce.ShippingService.dto.ShippingDto;
import com.Ketan.ECommerce.ShippingService.entities.ShippingOrder;
import com.Ketan.ECommerce.ShippingService.services.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping("/incomingDelivery")
    public ResponseEntity<String> incomingDeliveryOrders(@RequestBody OrderDto orderDto){
        String response =shippingService.incomingDeliveryOrders(orderDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deliveryBill/{id}")
    public ResponseEntity<BillDto> shippingBill(@PathVariable Long id){
        BillDto billDto = shippingService.shippingBill(id);
        return  ResponseEntity.ok(billDto);
    }
}
