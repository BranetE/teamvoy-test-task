package com.example.testtasktemvoy.dto;

import com.example.testtasktemvoy.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderProductDto {
    Product product;
    Integer quantity;
}
