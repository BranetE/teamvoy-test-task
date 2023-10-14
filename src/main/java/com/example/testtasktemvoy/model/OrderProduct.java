package com.example.testtasktemvoy.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class OrderProduct {
    @EmbeddedId
    private OrderProductPK pk;
    @Column
    private Long quantity;
}
