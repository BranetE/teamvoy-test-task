package com.example.testtasktemvoy.controller;

import com.example.testtasktemvoy.dto.CreateProductDto;
import com.example.testtasktemvoy.model.Product;
import com.example.testtasktemvoy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createProduct(@RequestBody CreateProductDto createProductDto){
        productService.createProduct(createProductDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateProduct(@RequestBody CreateProductDto updateProductDto, @PathVariable Long id){
        productService.updateProduct(id, updateProductDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> changeItemsLeft(@PathVariable Long id, @RequestParam("itemsLeft") Integer itemsLeft){
        productService.changeItemsLeft(id, itemsLeft);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
