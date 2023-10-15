package com.example.testtasktemvoy.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.testtasktemvoy.model.Product}
 */
@Data
public class CreateProductDto {
    String name;
    Integer price;
    Long itemsLeft;
}