package com.example.testtasktemvoy.service;

import com.example.testtasktemvoy.ModelUtils;
import com.example.testtasktemvoy.dto.CreateProductDto;
import com.example.testtasktemvoy.model.Product;
import com.example.testtasktemvoy.repository.ProductRepository;
import com.example.testtasktemvoy.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProductTest() {
        CreateProductDto createProductDto = ModelUtils.getCreateProductDto();
        Product product = ModelUtils.getProduct();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.createProduct(createProductDto);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProductTest() {
        CreateProductDto updateProductDto = ModelUtils.getCreateProductDto();
        Product product = ModelUtils.getProduct();

        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.updateProduct(1L, updateProductDto);

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testChangeItemsLeftTest() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).setItemsLeft(10, 1L);
        productService.changeItemsLeft(1L, 10);
        verify(productRepository).existsById(1L);
        verify(productRepository).setItemsLeft(10, 1L);
    }

    @Test
    void testGetAllProducts() {
        Product product = ModelUtils.getProduct();
        when(productRepository.findAll()).thenReturn(List.of(product));
        productService.getAllProducts();
        verify(productRepository).findAll();
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);
        productService.deleteProduct(1L);
    }
}
