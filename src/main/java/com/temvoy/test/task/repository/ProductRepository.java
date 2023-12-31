package com.temvoy.test.task.repository;

import com.temvoy.test.task.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query(value = "update products set items_left = :itemsLeft where id = :id",
    nativeQuery = true)
    void setItemsLeft(@Param("itemsLeft") Integer itemsLeft, @Param("id") Long id);
}
