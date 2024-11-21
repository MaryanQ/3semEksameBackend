package com.edu.eksamenbackend.Mapper;

import com.edu.eksamenbackend.dto.AlbumCustomerDTO;
import com.edu.eksamenbackend.entity.Album;
import com.edu.eksamenbackend.entity.AlbumCustomer;
import com.edu.eksamenbackend.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class AlbumCustomerMapper {

    public AlbumCustomerDTO toDTO(AlbumCustomer albumCustomer) {
        AlbumCustomerDTO dto = new AlbumCustomerDTO();
        dto.setId(albumCustomer.getId());
        dto.setCustomerId(albumCustomer.getCustomer().getId()); // Map the customerId
        dto.setAlbumId(albumCustomer.getAlbum().getId());
        dto.setAlbumTitle(albumCustomer.getAlbum().getTitle());
        dto.setAlbumArtist(albumCustomer.getAlbum().getArtist());
        dto.setInterestDate(albumCustomer.getInterestDate());
        dto.setAvailable(albumCustomer.getAlbum().isAvailable());
        dto.setReserved(albumCustomer.isReserved());
        dto.setLiked(albumCustomer.isLiked());
        return dto;
    }

    public AlbumCustomer toEntity(AlbumCustomerDTO dto, Customer customer, Album album) {
        AlbumCustomer entity = new AlbumCustomer();
        entity.setId(dto.getId());
        entity.setCustomer(customer); // Use the provided customer
        entity.setAlbum(album); // Use the provided album
        entity.setInterestDate(dto.getInterestDate());
        entity.setReserved(dto.isReserved());
        entity.setLiked(dto.isLiked());
        return entity;
    }
}
