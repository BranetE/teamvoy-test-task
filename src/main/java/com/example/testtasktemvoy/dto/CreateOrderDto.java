package com.example.testtasktemvoy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateOrderDto {
    private List<OrderProductDto> orderProducts;
    private String comment;
}
