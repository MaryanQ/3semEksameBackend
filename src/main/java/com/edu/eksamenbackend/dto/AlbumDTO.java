package com.edu.eksamenbackend.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AlbumDTO {
    private Long id;
    private String title;
    private String artist;
    private String genre; // Enum som String
    private boolean available;
    private Long storeId; // Reference til Store, hvis nødvendigt
}
