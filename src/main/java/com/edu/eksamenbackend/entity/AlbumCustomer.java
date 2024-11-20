package com.edu.eksamenbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AlbumCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDate interestDate; // Dato for interesse

    // Getters, Setters, Constructors
}
