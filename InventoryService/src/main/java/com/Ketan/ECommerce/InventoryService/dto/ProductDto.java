package com.Ketan.ECommerce.InventoryService.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private Integer stock;
    private Double price;
}
