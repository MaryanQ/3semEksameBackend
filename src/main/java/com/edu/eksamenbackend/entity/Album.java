package com.edu.eksamenbackend.entity;

import com.edu.eksamenbackend.enums.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private boolean available;

    @ManyToOne(fetch = FetchType.LAZY)  // Default is LAZY. Change to EAGER if you want to automatically fetch the store
    @JoinColumn(name = "store_id")
    private Store store;
    public Album() {
    }

    public Album(String title, String artist, Genre genre, boolean available, Store store) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.available = available;
        this.store = store;
    }

}
