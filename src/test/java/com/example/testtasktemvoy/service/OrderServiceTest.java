package com.example.testtasktemvoy.service;

import com.example.testtasktemvoy.ModelUtils;
import com.example.testtasktemvoy.dto.CreateOrderDto;
import com.example.testtasktemvoy.model.Order;
import com.example.testtasktemvoy.model.OrderStatus;
import com.example.testtasktemvoy.model.Product;
import com.example.testtasktemvoy.model.User;
import com.example.testtasktemvoy.repository.OrderRepository;
import com.example.testtasktemvoy.repository.ProductRepository;
import com.example.testtasktemvoy.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrderTest() {
        CreateOrderDto createOrderDto = ModelUtils.getCreateOrderDto();
        Order order =  ModelUtils.getOrder();
        User user = ModelUtils.getUser();
        Product product = ModelUtils.getProduct();

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));

        orderService.createOrder(createOrderDto, user);

        verify(orderRepository).save(any(Order.class));
        verify(productRepository).findById(anyLong());
    }
    @Test
    void updateOrderTest() {
        CreateOrderDto createOrderDto = ModelUtils.getCreateOrderDto();
        Order order = ModelUtils.getOrder();
        Product product = ModelUtils.getProduct();
        User user = ModelUtils.getUser();
        SecurityContextHolder.setContext(securityContext);


        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        orderService.updateOrder(1L, createOrderDto);

        verify(orderRepository).findById(anyLong());
        verify(orderRepository).save(any(Order.class));
        verify(productRepository).findById(anyLong());
    }

    @Test
    void testGetAllOrders(){
        Order order = ModelUtils.getOrder();

        when(orderRepository.findAll()).thenReturn(List.of(order));

        orderService.getAllOrders();

        verify(orderRepository).findAll();
    }

    @Test
    void testGetOrdersByUser(){
        Order order = ModelUtils.getOrder();

        when(orderRepository.getOrderByUserId(1L)).thenReturn(List.of(order));

        orderService.getOrdersByUser(1L);

        verify(orderRepository).getOrderByUserId(1L);
    }

    @Test
    void testDeleteOrder() {
        Order order = ModelUtils.getOrder();
        User user = ModelUtils.getUser();
        SecurityContextHolder.setContext(securityContext);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        doNothing().when(orderRepository).delete(any(Order.class));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        orderService.deleteOrder(1L);

        verify(orderRepository).findById(anyLong());
        verify(orderRepository).delete(any(Order.class));
    }

    @Test
    void testMArkOrderAsPaid() {

        when(orderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orderRepository).setOrderStatus(1L, OrderStatus.PAID.toString());

        orderService.markOrderAsPaid(1L);

        verify(orderRepository).existsById(1L);
        verify(orderRepository).setOrderStatus(1L, OrderStatus.PAID.toString());
    }
}
