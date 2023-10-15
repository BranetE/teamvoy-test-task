package com.example.testtasktemvoy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String comment;
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @JsonManagedReference
    @OneToMany(mappedBy = "pk.order")
    private List<OrderProduct> orderProducts;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    public Double getTotalPrice(){
        return orderProducts.stream()
                .map(OrderProduct::getTotalPrice)
                .reduce(0.0, Double::sum);
    }
}
