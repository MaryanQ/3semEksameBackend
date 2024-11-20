package com.edu.eksamenbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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

    private LocalDate interestDate;
    private boolean reserved;
    private boolean liked;

    public AlbumCustomer() {
    }

    public AlbumCustomer(Album album, Customer customer, LocalDate interestDate, boolean reserved, boolean liked) {
        this.album = album;
        this.customer = customer;
        this.interestDate = interestDate;
        this.reserved = reserved;
        this.liked = liked;
    }

}
