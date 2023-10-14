package com.example.testtasktemvoy.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private Role role;
    @Column
    private String firstName;
    @Column
    private String lastName;
}
