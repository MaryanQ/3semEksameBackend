package com.edu.eksamenbackend.Mapper;

import com.edu.eksamenbackend.dto.AlbumCustomerDTO;
import com.edu.eksamenbackend.dto.AlbumDTO;
import com.edu.eksamenbackend.entity.Album;
import com.edu.eksamenbackend.entity.AlbumCustomer;
import com.edu.eksamenbackend.enums.Genre;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {

    public AlbumDTO toDTO(Album album) {
        AlbumDTO dto = new AlbumDTO();
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setArtist(album.getArtist());
        dto.setGenre(album.getGenre());  // Directly set Genre
        dto.setAvailable(album.isAvailable());
        return dto;
    }

    public Album toEntity(AlbumDTO dto) {
        Album album = new Album();
        album.setId(dto.getId());
        album.setTitle(dto.getTitle());
        album.setArtist(dto.getArtist());
        album.setGenre(dto.getGenre());  // Directly set Genre
        album.setAvailable(dto.isAvailable());
        return album;
    }
}