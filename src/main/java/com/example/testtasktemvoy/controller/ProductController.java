package com.example.testtasktemvoy.controller;

import com.example.testtasktemvoy.dto.CreateProductDto;
import com.example.testtasktemvoy.model.Product;
import com.example.testtasktemvoy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> createProduct(@Valid @RequestBody CreateProductDto createProductDto){

        productService.createProduct(createProductDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> updateProduct(@Valid @RequestBody CreateProductDto createProductDto, @PathVariable Long id){
        productService.updateProduct(id, createProductDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> changeItemsLeft(@PathVariable Long id, @RequestParam("itemsLeft") Integer itemsLeft){
        productService.changeItemsLeft(id, itemsLeft);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
