package com.temvoy.test.task.service;

import com.temvoy.test.task.dto.CreateProductDto;
import com.temvoy.test.task.model.Product;

import java.util.List;

public interface ProductService {
    void createProduct(CreateProductDto createProductDto);
    void updateProduct(Long id, CreateProductDto updateProductDto);
    void changeItemsLeft(Long id, Integer itemsLeft);
    List<Product> getAllProducts();
    void deleteProduct(Long id);
}
