package com.example.testtasktemvoy.service.impl;

import com.example.testtasktemvoy.dto.CreateOrderDto;
import com.example.testtasktemvoy.dto.CreateOrderProductDto;
import com.example.testtasktemvoy.dto.OrderDto;
import com.example.testtasktemvoy.dto.mapper.OrderDtoMapper;
import com.example.testtasktemvoy.exception.ErrorMessage;
import com.example.testtasktemvoy.model.Order;
import com.example.testtasktemvoy.model.OrderProduct;
import com.example.testtasktemvoy.model.OrderStatus;
import com.example.testtasktemvoy.model.Product;
import com.example.testtasktemvoy.repository.OrderProductRepository;
import com.example.testtasktemvoy.repository.OrderRepository;
import com.example.testtasktemvoy.repository.ProductRepository;
import com.example.testtasktemvoy.service.OrderService;
import com.example.testtasktemvoy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Override
    public void createOrder(CreateOrderDto createOrderDto) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.UNPAID);
        order.setComment(createOrderDto.getComment());

        List<OrderProduct> orderProductList = getOrderProductsFromDto(createOrderDto.getCreateOrderProductDtoList(), order);

        order.setOrderProducts(orderProductList);
        orderRepository.save(order);
        orderProductRepository.saveAll(orderProductList);
    }

    @Override
    public void updateOrder(Long id, CreateOrderDto updateOrderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id));
        order.setComment(updateOrderDto.getComment());

        restoreProductsQuantity(order.getOrderProducts());
        orderProductRepository.deleteAll(order.getOrderProducts());

        List<OrderProduct> orderProductList = getOrderProductsFromDto(updateOrderDto.getCreateOrderProductDtoList(), order);

        order.setOrderProducts(orderProductList);
        orderProductRepository.saveAll(orderProductList);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Order::getId))
                .map(OrderDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.getOrderByUserId(userId);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id));
        restoreProductsQuantity(order.getOrderProducts());
        orderProductRepository.deleteAll(order.getOrderProducts());
        orderRepository.delete(order);
    }

    @Override
    public void markOrderAsPaid(Long id) {
        if(!orderRepository.existsById(id)) throw new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id);
        orderRepository.setOrderStatus(id, OrderStatus.PAID.toString());
    }

    private List<OrderProduct> getOrderProductsFromDto(List<CreateOrderProductDto> createOrderProductDtoList, Order order){
        return createOrderProductDtoList.stream()
                .map(o -> {
                    Product product = productRepository.findById(o.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_DOESNT_EXIST + o.getProductId()));
                    if(product.getItemsLeft() - o.getQuantity() >= 0) {
                        product.setItemsLeft(product.getItemsLeft() - o.getQuantity());
                    } else {
                        throw new InputMismatchException(ErrorMessage.OUT_OF_PRODUCTS);
                    }
                    return new OrderProduct(order, product, o.getQuantity());
                }
                )
                .collect(Collectors.toList());
    }

    private void restoreProductsQuantity(List<OrderProduct> orderProductList){
        orderProductList.forEach(
                o -> {
                    Integer currentQuantity = o.getPk().getProduct().getItemsLeft();
                    o.getPk().getProduct().setItemsLeft(currentQuantity + o.getQuantity());
                }
        );
    }
}
