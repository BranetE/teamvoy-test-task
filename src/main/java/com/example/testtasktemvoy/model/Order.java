package com.example.testtasktemvoy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String comment;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @JsonManagedReference
    @OneToMany(mappedBy = "pk.order", fetch = FetchType.EAGER,cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
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
