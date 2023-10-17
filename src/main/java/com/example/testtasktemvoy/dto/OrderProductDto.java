package com.example.testtasktemvoy.dto;

import com.example.testtasktemvoy.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderProductDto {
    Product product;
    Integer quantity;
}
