package com.example.testtasktemvoy.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CreateProductDto {
    @NotBlank
    String name;
    String description;
    @Min(10)
    Double price;
    @Min(1)
    Integer itemsLeft;
}