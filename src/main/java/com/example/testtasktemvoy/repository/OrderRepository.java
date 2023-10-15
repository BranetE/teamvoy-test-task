package com.example.testtasktemvoy.repository;

import com.example.testtasktemvoy.model.Order;
import com.example.testtasktemvoy.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrderByUserId(Long userId);

    @Modifying
    @Query(value = "update order set order_status = :status",
    nativeQuery = true)
    void setOrderStatus(@Param("id") Long id, @Param("status") OrderStatus orderStatus);
}
