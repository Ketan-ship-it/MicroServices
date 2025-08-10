package com.Ketan.ECommerce.OrderService.controller;

import com.Ketan.ECommerce.OrderService.clients.InventoryFeignClient;
import com.Ketan.ECommerce.OrderService.dto.OrderCancelDto;
import com.Ketan.ECommerce.OrderService.dto.OrderDto;
import com.Ketan.ECommerce.OrderService.entities.CancelledOrder;
import com.Ketan.ECommerce.OrderService.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class OrderController {

    @Value("${my.variable}")
    private String variable;

    private final OrderService orderService;

    @GetMapping("/helloOrder")
    public String helloOrder(){
        return "hello from order service" + variable;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        List<OrderDto> inventory=orderService.getAllOrders();
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<OrderDto>> getOrderDto(@PathVariable Long id){
        Optional<OrderDto> inventory=orderService.getOrderById(id);
        return ResponseEntity.ok(inventory);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        OrderDto orderDto1 = orderService.createOrder(orderDto);
        return ResponseEntity.ok(orderDto1);
    }

    @PutMapping("/sendForDelivery/{order_id}")
    public ResponseEntity<OrderDto> sendForDelivery(@PathVariable Long order_id){
        OrderDto orderDto = orderService.checkBeforeSending(order_id);
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/cancelOrder")
    public ResponseEntity<CancelledOrder> cancelOrder(@RequestBody OrderCancelDto orderCancelDto){
        CancelledOrder cancelledOrder = orderService.cancelOrder(orderCancelDto);
        return ResponseEntity.ok(cancelledOrder);
    }

}
