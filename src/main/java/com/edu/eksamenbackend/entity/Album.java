package com.edu.eksamenbackend.entity;

import com.edu.eksamenbackend.enums.Genre;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String artist;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private boolean available;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<AlbumCustomer> albumCustomers;

    // Getters, Setters, Constructors
}

