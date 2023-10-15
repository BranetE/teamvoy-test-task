package com.example.testtasktemvoy.service.impl;

import com.example.testtasktemvoy.exception.ErrorMessage;
import com.example.testtasktemvoy.model.Product;
import com.example.testtasktemvoy.repository.ProductRepository;
import com.example.testtasktemvoy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long id, Product product) {
        Product currentProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_DOESNT_EXIST + id));
        product.setId(currentProduct.getId());
        productRepository.save(product);
        
    }

    @Override
    public void changeItemsLeft(Long id, Integer itemsLeft) {
        if(!productRepository.existsById(id)) throw new EntityNotFoundException(ErrorMessage.PRODUCT_DOESNT_EXIST + id);
        productRepository.setItemsLeft(itemsLeft, id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) throw new EntityNotFoundException(ErrorMessage.PRODUCT_DOESNT_EXIST + id);
        productRepository.deleteById(id);
    }
}
