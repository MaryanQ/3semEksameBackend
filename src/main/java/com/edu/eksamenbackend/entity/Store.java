package com.edu.eksamenbackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String street;
    private String city;
    private String zip;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Album> albums;

    // Getters, Setters, Constructors
}
