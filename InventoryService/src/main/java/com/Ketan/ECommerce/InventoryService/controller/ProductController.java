package com.Ketan.ECommerce.InventoryService.controller;

import com.Ketan.ECommerce.InventoryService.dto.OrderRequestDto;
import com.Ketan.ECommerce.InventoryService.dto.ProductDto;
import com.Ketan.ECommerce.InventoryService.services.ProductServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductServices productServices;

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
