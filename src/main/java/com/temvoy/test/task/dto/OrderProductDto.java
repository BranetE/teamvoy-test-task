package com.temvoy.test.task.dto;

import com.temvoy.test.task.model.Product;
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
