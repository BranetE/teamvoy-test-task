package com.example.testtasktemvoy.dto.mapper;

import com.example.testtasktemvoy.dto.OrderDto;
import com.example.testtasktemvoy.dto.OrderProductDto;
import com.example.testtasktemvoy.model.Order;

import java.util.stream.Collectors;

public class OrderDtoMapper {
    public static OrderDto convertToDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setStatus(order.getOrderStatus().name());
        orderDto.setPrice(order.getTotalPrice());
        orderDto.setComment(orderDto.getComment());
        orderDto.setProducts(order.getOrderProducts().stream()
                .map(o -> new OrderProductDto(o.getPk().getProduct(), o.getQuantity()))
                .collect(Collectors.toList()));
        return orderDto;
    }
}
