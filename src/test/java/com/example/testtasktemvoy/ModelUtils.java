package com.example.testtasktemvoy;

import com.example.testtasktemvoy.dto.CreateOrderDto;
import com.example.testtasktemvoy.dto.CreateOrderProductDto;
import com.example.testtasktemvoy.dto.CreateProductDto;
import com.example.testtasktemvoy.model.Order;
import com.example.testtasktemvoy.model.OrderStatus;
import com.example.testtasktemvoy.model.Product;
import com.example.testtasktemvoy.model.Role;
import com.example.testtasktemvoy.model.User;

import java.util.List;

public class ModelUtils {
    public static CreateOrderDto getCreateOrderDto(){
        CreateOrderProductDto orderProductDto = CreateOrderProductDto.builder()
                .productId(1L)
                .quantity(10)
                .build();
        return CreateOrderDto.builder()
                .products(List.of(orderProductDto))
                .comment("Comment")
                .build();
    }

    public static Order getOrder(){
        return Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.UNPAID)
                .comment("Comment")
                .orderProducts(List.of())
                .user(getUser())
                .build();
    }

    public static CreateProductDto getCreateProductDto(){
        return CreateProductDto.builder()
                .name("iPhone 15")
                .price(1500.0)
                .description("color: black, memory: 1 TB")
                .itemsLeft(25)
                .build();
    }

    public static Product getProduct(){
        return Product.builder()
                .id(1L)
                .price(100.0)
                .description("description")
                .itemsLeft(50)
                .build();
    }

    public static User getUser(){
        return new User(1L,"user@mail.com", Role.USER, "User", "User", "pwd");
    }
}
