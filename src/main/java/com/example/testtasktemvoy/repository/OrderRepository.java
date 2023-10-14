package com.example.testtasktemvoy.repository;

import com.example.testtasktemvoy.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrderByUserId(Long userId);
}
