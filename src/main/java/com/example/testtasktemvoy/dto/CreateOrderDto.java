package com.example.testtasktemvoy.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {
    private List<OrderProductDto> orderProducts;
    private String comment;
}
