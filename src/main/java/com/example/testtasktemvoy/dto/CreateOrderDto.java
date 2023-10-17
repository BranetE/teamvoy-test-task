package com.example.testtasktemvoy.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {
    private List<CreateOrderProductDto> products;
    private String comment;
}
