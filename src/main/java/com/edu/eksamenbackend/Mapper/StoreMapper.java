package com.edu.eksamenbackend.Mapper;

import com.edu.eksamenbackend.dto.StoreDTO;
import com.edu.eksamenbackend.entity.Store;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class StoreMapper {

    private final AlbumMapper albumMapper;

    public StoreMapper(AlbumMapper albumMapper) {
        this.albumMapper = albumMapper;
    }

    // Map Store til StoreDTO
    public StoreDTO toDTO(Store store) {
        StoreDTO dto = new StoreDTO();
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setStreet(store.getStreet());
        dto.setCity(store.getCity());
        dto.setZip(store.getZip());
        dto.setAlbums(store.getAlbums().stream()
                .map(albumMapper::toDTO) // Map albums til AlbumDTO
                .collect(Collectors.toList()));
        return dto;
    }

    // Map StoreDTO til Store
    public Store toEntity(StoreDTO dto) {
        Store store = new Store();
        store.setId(dto.getId());
        store.setName(dto.getName());
        store.setStreet(dto.getStreet());
        store.setCity(dto.getCity());
        store.setZip(dto.getZip());
        // Albums håndteres separat for at undgå kaskadeproblemer
        return store;
    }
}
