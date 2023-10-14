package com.example.testtasktemvoy.repository;

import com.example.testtasktemvoy.model.OrderProduct;
import com.example.testtasktemvoy.model.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}
