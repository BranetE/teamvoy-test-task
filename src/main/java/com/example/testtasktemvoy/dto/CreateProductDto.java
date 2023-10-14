package com.example.testtasktemvoy.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.testtasktemvoy.model.Product}
 */
@Value
public class CreateProductDto implements Serializable {
    String name;
    Integer price;
    Long itemsLeft;
}