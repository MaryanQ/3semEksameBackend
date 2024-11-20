package com.edu.eksamenbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AlbumCustomerDTO {

    private Long id;
    private Long albumId;
    private String albumTitle;
    private String albumArtist;
    private LocalDate interestDate;
    private boolean available;
    private boolean reserved;
    private boolean liked;

}
