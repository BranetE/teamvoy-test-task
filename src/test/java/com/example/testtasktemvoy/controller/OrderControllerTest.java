package com.example.testtasktemvoy.controller;

import com.example.testtasktemvoy.ModelUtils;
import com.example.testtasktemvoy.dto.CreateOrderDto;
import com.example.testtasktemvoy.model.User;
import com.example.testtasktemvoy.service.OrderService;
import com.example.testtasktemvoy.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private OrderServiceImpl orderService;

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    @SneakyThrows
    @WithUserDetails
    void testGetOrders() {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails
    void testGetOrdersByUser() {
        mockMvc.perform(get("/api/orders/getByUser/1"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testCreateOrder() {
        CreateOrderDto createOrderDto = ModelUtils.getCreateOrderDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(createOrderDto);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    @WithUserDetails
    void testUpdateOrder() {
        CreateOrderDto createOrderDto = ModelUtils.getCreateOrderDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(createOrderDto);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails
    void testDeleteOrder() {
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails
    void testMarkOrderAsPaid() {
        mockMvc.perform(patch("/api/orders/1/pay"))
                .andExpect(status().isOk());
    }
}
