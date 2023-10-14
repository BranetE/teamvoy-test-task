package com.example.testtasktemvoy.service;

import com.example.testtasktemvoy.dto.CreateOrderDto;
import com.example.testtasktemvoy.dto.CreateProductDto;
import com.example.testtasktemvoy.model.Order;

import java.util.List;

public interface OrderService {
    void createOrder(CreateOrderDto createOrderDto);
    void updateOrder(Long id, CreateOrderDto updateOrderDto);
    List<Order> getAllOrders();
    List<Order> getOrdersByUser(Long userId);
    void deleteOrder(Long id);
}
