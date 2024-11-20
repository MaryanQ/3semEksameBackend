package com.edu.eksamenbackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<AlbumCustomer> albumCustomers;

    // Getters, Setters, Constructors
}

