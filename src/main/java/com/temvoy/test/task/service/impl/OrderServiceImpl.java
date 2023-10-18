package com.temvoy.test.task.service.impl;

import com.temvoy.test.task.dto.CreateOrderDto;
import com.temvoy.test.task.dto.CreateOrderProductDto;
import com.temvoy.test.task.dto.OrderDto;
import com.temvoy.test.task.dto.mapper.OrderDtoMapper;
import com.temvoy.test.task.exception.ErrorMessage;
import com.temvoy.test.task.model.Order;
import com.temvoy.test.task.model.OrderProduct;
import com.temvoy.test.task.model.OrderStatus;
import com.temvoy.test.task.model.Product;
import com.temvoy.test.task.model.Role;
import com.temvoy.test.task.model.User;
import com.temvoy.test.task.repository.OrderRepository;
import com.temvoy.test.task.repository.ProductRepository;
import com.temvoy.test.task.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final Timer timer = new Timer();

    @Override
    public void createOrder(CreateOrderDto createOrderDto, User user) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.UNPAID);
        order.setComment(createOrderDto.getComment());
        order.setUser(user);

        List<OrderProduct> orderProductList = getOrderProductsFromDto(createOrderDto.getProducts(), order);

        order.setOrderProducts(orderProductList);
        Order savedOrder = orderRepository.save(order);

        deleteAfterTenMinutesIfUnpaid(savedOrder.getId());
    }

    private void deleteAfterTenMinutesIfUnpaid(Long id){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id));
                if(order.getOrderStatus().equals(OrderStatus.UNPAID)){
                    restoreProductsQuantity(order.getOrderProducts());
                    orderRepository.delete(order);
                }
            }
        };

        long delay = 1000 * 60 * 10;
        timer.schedule(task, delay);
    }

    @Override
    public void updateOrder(Long id, CreateOrderDto updateOrderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id));

        checkIfPrincipalHasAccess(order);

        order.setComment(updateOrderDto.getComment());

        restoreProductsQuantity(order.getOrderProducts());

        List<OrderProduct> orderProductList = getOrderProductsFromDto(updateOrderDto.getProducts(), order);

        order.setOrderProducts(orderProductList);
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
    public List<OrderDto> getOrdersByUser(Long userId) {
        return orderRepository.getOrderByUserId(userId).stream()
                .sorted(Comparator.comparingLong(Order::getId))
                .map(OrderDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id));

        checkIfPrincipalHasAccess(order);

        restoreProductsQuantity(order.getOrderProducts());
        orderRepository.delete(order);
    }

    @Override
    public void markOrderAsPaid(Long id) {
        if(!orderRepository.existsById(id)) throw new EntityNotFoundException(ErrorMessage.ORDER_DOESNT_EXIST + id);
        orderRepository.setOrderStatus(id, OrderStatus.PAID.toString());
    }

    private void checkIfPrincipalHasAccess(Order order){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!Objects.equals(currentUser.getId(), order.getUser().getId())
                && !currentUser.getRole().equals(Role.ADMIN)){
            throw new AccessDeniedException(ErrorMessage.USER_DOESNT_HAVE_ACCESS);
        }
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
                    productRepository.save(o.getPk().getProduct());
                }
        );
    }
}
