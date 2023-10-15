package com.example.testtasktemvoy.dto;

import com.example.testtasktemvoy.model.Product;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class OrderProductDto {
//    private Product product;
    private Long productId;
    private Integer quantity;

}
