package com.example.testtasktemvoy.service.impl;

import com.example.testtasktemvoy.dto.CreateOrderDto;
import com.example.testtasktemvoy.dto.OrderProductDto;
import com.example.testtasktemvoy.exception.ErrorMessage;
import com.example.testtasktemvoy.model.Order;
import com.example.testtasktemvoy.model.OrderProduct;
import com.example.testtasktemvoy.model.OrderStatus;
import com.example.testtasktemvoy.model.Product;
import com.example.testtasktemvoy.repository.OrderProductRepository;
import com.example.testtasktemvoy.repository.OrderRepository;
import com.example.testtasktemvoy.repository.ProductRepository;
import com.example.testtasktemvoy.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Override
    public void createOrder(CreateOrderDto createOrderDto) {
        Order order = new Order();
        order.setComment(createOrderDto.getComment());

        List<OrderProduct> orderProductList = getOrderProductListFromDto(createOrderDto.getOrderProducts(), order);

        order.setOrderProducts(orderProductList);
        orderRepository.save(order);
    }

    @Override
    public void updateOrder(Long id, CreateOrderDto updateOrderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id));
        order.setComment(updateOrderDto.getComment());

        List<OrderProduct> orderProductList = getOrderProductListFromDto(updateOrderDto.getOrderProducts(), order);

        order.setOrderProducts(orderProductList);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.getOrderByUserId(userId);
    }

    @Override
    public void deleteOrder(Long id) {
        if(!orderRepository.existsById(id)) throw new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id);
        orderRepository.deleteById(id);
    }

    @Override
    public void markOrderAsPaid(Long id) {
        if(!orderRepository.existsById(id)) throw new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id);
        orderRepository.setOrderStatus(id, OrderStatus.PAID);
    }

    private List<OrderProduct> getOrderProductListFromDto(List<OrderProductDto> orderProductDtoList, Order order){

        List<OrderProduct> orderProductList = new ArrayList<>();

        orderProductDtoList
                .forEach(o -> {
                    Product product = productRepository.findById(o.getProductId()).orElseThrow(EntityNotFoundException::new);
                    OrderProduct orderProduct = new OrderProduct(order, product, o.getQuantity());
                    orderProductList.add(orderProduct);
                    orderProductRepository.save(orderProduct);
                });

        return orderProductList;
    }
}
