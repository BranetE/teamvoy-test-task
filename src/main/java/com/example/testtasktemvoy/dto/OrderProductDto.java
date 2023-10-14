package com.example.testtasktemvoy.dto;

import com.example.testtasktemvoy.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductDto {
//    private Product product;
    private Long productId;
    private Integer quantity;

}
