package com.example.testtasktemvoy.service;

import com.example.testtasktemvoy.dto.CreateOrderDto;
import com.example.testtasktemvoy.dto.OrderDto;
import com.example.testtasktemvoy.model.Order;

import java.util.List;

public interface OrderService {
    void createOrder(CreateOrderDto createOrderDto);
    void updateOrder(Long id, CreateOrderDto updateOrderDto);
    List<OrderDto> getAllOrders();
    List<Order> getOrdersByUser(Long userId);
    void deleteOrder(Long id);
    void markOrderAsPaid(Long id);
}
