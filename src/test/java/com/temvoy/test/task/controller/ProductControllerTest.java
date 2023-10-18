package com.temvoy.test.task.controller;

import com.temvoy.test.task.ModelUtils;
import com.temvoy.test.task.dto.CreateProductDto;
import com.temvoy.test.task.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductServiceImpl productService;

    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    @SneakyThrows
    void testGetProducts() {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testCreateProduct() {
        CreateProductDto createProductDto = ModelUtils.getCreateProductDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(createProductDto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void testUpdateProduct() {
        CreateProductDto createProductDto = ModelUtils.getCreateProductDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(createProductDto);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testChangeItemsLeft() {
        mockMvc.perform(patch("/api/products/1")
                        .param("itemsLeft", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testDeleteProduct() {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());
    }
}
