package com.edu.eksamenbackend.Mapper;

import com.edu.eksamenbackend.dto.AlbumCustomerDTO;
import com.edu.eksamenbackend.entity.Album;
import com.edu.eksamenbackend.entity.AlbumCustomer;
import com.edu.eksamenbackend.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class AlbumCustomerMapper {

    // Convert from Entity to DTO
    public AlbumCustomerDTO toDTO(AlbumCustomer albumCustomer) {
        AlbumCustomerDTO dto = new AlbumCustomerDTO();
        dto.setId(albumCustomer.getId());
        dto.setAlbumId(albumCustomer.getAlbum().getId());
        dto.setAlbumTitle(albumCustomer.getAlbum().getTitle());
        dto.setAlbumArtist(albumCustomer.getAlbum().getArtist());
        dto.setInterestDate(albumCustomer.getInterestDate());
        dto.setAvailable(albumCustomer.getAlbum().isAvailable());
        dto.setReserved(albumCustomer.isReserved());
        dto.setLiked(albumCustomer.isLiked());
        return dto;
    }

    // Convert from DTO to Entity
    public AlbumCustomer toEntity(AlbumCustomerDTO dto, Customer customer, Album album) {
        AlbumCustomer albumCustomer = new AlbumCustomer();
        albumCustomer.setId(dto.getId());
        albumCustomer.setAlbum(album);
        albumCustomer.setCustomer(customer);
        albumCustomer.setInterestDate(dto.getInterestDate());
        albumCustomer.setReserved(dto.isReserved());
        albumCustomer.setLiked(dto.isLiked());
        return albumCustomer;
    }
}
