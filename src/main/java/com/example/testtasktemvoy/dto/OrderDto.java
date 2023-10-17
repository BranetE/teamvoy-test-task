package com.example.testtasktemvoy.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    Long id;
    String comment;
    String status;
    List<OrderProductDto> products;
    Long userId;
    Double price;
}
