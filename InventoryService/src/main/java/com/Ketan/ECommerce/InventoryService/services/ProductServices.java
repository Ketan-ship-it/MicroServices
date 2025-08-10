package com.Ketan.ECommerce.InventoryService.services;

import com.Ketan.ECommerce.InventoryService.dto.OrderItemDto;
import com.Ketan.ECommerce.InventoryService.dto.OrderRequestDto;
import com.Ketan.ECommerce.InventoryService.dto.ProductDto;
import com.Ketan.ECommerce.InventoryService.entity.Product;
import com.Ketan.ECommerce.InventoryService.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServices {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServices(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductDto> getAllProducts(){
        log.info("Getting all the products in the Inventory");
        List<Product> inventory = productRepository.findAll();
        return inventory.stream()
                .map((element) -> modelMapper.map(element, ProductDto.class))
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(Long id){
        log.info("Getting  products in the Inventory by id :{}", id);
        Optional<Product> inventory = productRepository.findById(id);
        return inventory.map((element) -> modelMapper.map(element, ProductDto.class));
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Checking and reducing all the items in orderRequestDto, if in stock");
        double total =0.0;
        for(OrderItemDto orderItemDto: orderRequestDto.getOrderItems()){
            Long productId = orderItemDto.getProduct_id();
            Integer quantity = orderItemDto.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(()-> new RuntimeException("No such Product with id:"+productId));
            if(product.getStock()<quantity){
                throw new RuntimeException("The requested quantity of product cannot be fulfilled");
            }
            product.setStock(product.getStock() - quantity);
            total+=quantity*product.getPrice();
        }
        return total;
    }

    @Transactional 
    public Double increaseStocks(OrderRequestDto orderRequestDto) {

        Double total_price = orderRequestDto.getTotalPrice();

        log.info("Checking and putting back all the items in inventory");

        for(OrderItemDto orderItemDto: orderRequestDto.getOrderItems()){
            Long productId = orderItemDto.getProduct_id();
            Integer quantity = orderItemDto.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(()-> new RuntimeException("No such Product with id:"+productId));

            product.setStock(product.getStock() + quantity);
            productRepository.save(product);
            total_price-=quantity*product.getPrice();
        }
        return total_price;
    }
}
