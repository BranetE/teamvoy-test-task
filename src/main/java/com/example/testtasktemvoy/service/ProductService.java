package com.example.testtasktemvoy.service;

import com.example.testtasktemvoy.dto.CreateProductDto;
import com.example.testtasktemvoy.model.Product;

import java.util.List;

public interface ProductService {
    void createProduct(CreateProductDto createProductDto);
    void updateProduct(Long id, CreateProductDto updateProductDto);
    void changeItemsLeft(Long id, Integer itemsLeft);
    List<Product> getAllProducts();
    void deleteProduct(Long id);
}
