package com.Ketan.ECommerce.InventoryService.controller;

import com.Ketan.ECommerce.InventoryService.client.OrdersFeignClient;
import com.Ketan.ECommerce.InventoryService.dto.OrderRequestDto;
import com.Ketan.ECommerce.InventoryService.dto.ProductDto;
import com.Ketan.ECommerce.InventoryService.services.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServices productServices;
//    private final DiscoveryClient discoveryClient;
//    private final RestClient restClient;
    private final OrdersFeignClient ordersFeignClient;

    public ProductController(ProductServices productServices, DiscoveryClient discoveryClient, RestClient restClient, OrdersFeignClient ordersFeignClient) {
        this.productServices = productServices;
        this.ordersFeignClient = ordersFeignClient;
//        this.discoveryClient = discoveryClient;
//        this.restClient = restClient;
    }

    @GetMapping("/fetchOrder")
    public String fetchOrder(){
        log.info("Calling order-service now");
//        ServiceInstance orders = discoveryClient.getInstances("order-service").getFirst();
//        return restClient.get()
//                .uri(orders.getUri() + "/orders/core/helloOrder")
//                .retrieve()
//                .body(String.class);
        try {
            return ordersFeignClient.helloOrders();
        } catch (Exception e){
            log.error("Some error occurred");
            return e.getMessage();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> inventory=productServices.getAllProducts();
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<ProductDto>> getProductsById(@PathVariable Long id){
        Optional<ProductDto> inventory=productServices.getProductById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/reduceStocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto){
        Double totalPrice = productServices.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }

    @PutMapping("/addStocks")
    public ResponseEntity<Double> addStocks(@RequestBody OrderRequestDto orderRequestDto){
        Double reducedPrice = productServices.increaseStocks(orderRequestDto);
        return ResponseEntity.ok(reducedPrice);
    }
}
