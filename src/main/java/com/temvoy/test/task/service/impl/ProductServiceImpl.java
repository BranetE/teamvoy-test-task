package com.temvoy.test.task.service.impl;

import com.temvoy.test.task.dto.CreateProductDto;
import com.temvoy.test.task.exception.ErrorMessage;
import com.temvoy.test.task.model.Product;
import com.temvoy.test.task.repository.ProductRepository;
import com.temvoy.test.task.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void createProduct(CreateProductDto createProductDto) {
        Product product = new Product();
        product.setName(createProductDto.getName());
        product.setDescription(createProductDto.getDescription());
        product.setPrice(createProductDto.getPrice());
        product.setItemsLeft(createProductDto.getItemsLeft());
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long id, CreateProductDto updateProductDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_DOESNT_EXIST + id));
        product.setName(updateProductDto.getName());
        product.setPrice(updateProductDto.getPrice());
        product.setItemsLeft(updateProductDto.getItemsLeft());
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
