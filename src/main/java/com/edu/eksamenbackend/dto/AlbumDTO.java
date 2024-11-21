package com.edu.eksamenbackend.dto;
import com.edu.eksamenbackend.enums.Genre;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AlbumDTO {
    private Long id;
    private String title;
    private String artist;
    private Genre genre;
    private boolean available;
    private String storeName;
}
