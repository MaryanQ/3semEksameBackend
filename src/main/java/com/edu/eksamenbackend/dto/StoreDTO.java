package com.edu.eksamenbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreDTO {
    private Long id;
    private String name;
    private String street;
    private String city;
    private String zip;
    private List<AlbumDTO> albums;

}
