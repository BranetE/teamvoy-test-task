package com.example.testtasktemvoy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    Long id;
    String comment;
    String status;
    List<OrderProductDto> products;
    Long userId;
    Double price;
}
