package com.example.testtasktemvoy.controller;

import com.example.testtasktemvoy.dto.CreateOrderDto;
import com.example.testtasktemvoy.dto.OrderDto;
import com.example.testtasktemvoy.model.Order;
import com.example.testtasktemvoy.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUser(userId));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createOrder(@RequestBody @Valid CreateOrderDto createOrderDto) {
        orderService.createOrder(createOrderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateOrder(@PathVariable Long id, @RequestBody @Valid CreateOrderDto updateOrderDto) {
        orderService.updateOrder(id, updateOrderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<HttpStatus> markOrderAsPaid(@PathVariable Long id){
        orderService.markOrderAsPaid(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}