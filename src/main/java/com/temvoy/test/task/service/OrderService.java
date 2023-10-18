package com.temvoy.test.task.service;

import com.temvoy.test.task.dto.CreateOrderDto;
import com.temvoy.test.task.dto.OrderDto;
import com.temvoy.test.task.model.User;

import java.util.List;

public interface OrderService {
    void createOrder(CreateOrderDto createOrderDto, User user);
    void updateOrder(Long id, CreateOrderDto updateOrderDto);
    List<OrderDto> getAllOrders();
    List<OrderDto> getOrdersByUser(Long userId);
    void deleteOrder(Long id);
    void markOrderAsPaid(Long id);
}
