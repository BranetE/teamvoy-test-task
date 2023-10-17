package com.example.testtasktemvoy.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateOrderProductDto {
    @NotNull
    private Long productId;
    @Min(1)
    private Integer quantity;

}
