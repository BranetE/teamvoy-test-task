package com.temvoy.test.task.repository;

import com.temvoy.test.task.model.OrderProduct;
import com.temvoy.test.task.model.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {

    @Modifying
    @Query(value = "delete from order_products where order_id = :orderId",
    nativeQuery = true)
    void deleteAllByOrderId(@Param("orderId") Long orderId);
}
