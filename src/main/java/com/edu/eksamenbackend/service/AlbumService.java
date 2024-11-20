package com.edu.eksamenbackend.service;

import com.edu.eksamenbackend.Mapper.AlbumMapper;
import com.edu.eksamenbackend.dto.AlbumDTO;
import com.edu.eksamenbackend.entity.Album;
import com.edu.eksamenbackend.entity.Store;
import com.edu.eksamenbackend.enums.Genre;
import com.edu.eksamenbackend.repository.AlbumRepository;
import com.edu.eksamenbackend.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final StoreRepository storeRepository; // Add the store repository to fetch store details

    @Autowired
    public AlbumService(AlbumRepository albumRepository, AlbumMapper albumMapper, StoreRepository storeRepository) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.storeRepository = storeRepository;
    }

    // A. Create a new album
    public AlbumDTO createAlbum(AlbumDTO albumDTO) {
        // Ensure that the storeId is provided in the DTO
        if (albumDTO.getStoreId() == null) {
            throw new RuntimeException("Store ID cannot be null");
        }

        // Fetch the store by ID using the storeRepository
        Store store = storeRepository.findById(albumDTO.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        // Convert the AlbumDTO to Album entity
        Album album = albumMapper.toEntity(albumDTO);

        // Assign the store to the album
        album.setStore(store);

        // Save the album and convert it back to DTO
        album = albumRepository.save(album);

        // Return the created album DTO
        return albumMapper.toDTO(album);
    }

    // B. Get all albums
    public List<AlbumDTO> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream()
                .map(album -> {
                    AlbumDTO dto = albumMapper.toDTO(album);
                    if (album.getStore() != null) {
                        dto.setStoreId(album.getStore().getId());  // Explicitly set storeId in the DTO
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // C. Get details of a single album
    public AlbumDTO getAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        AlbumDTO albumDTO = albumMapper.toDTO(album);
        if (album.getStore() != null) {
            albumDTO.setStoreId(album.getStore().getId());  // Explicitly set storeId in the DTO
        }
        return albumDTO;
    }

    // D. Update an existing album
    public AlbumDTO updateAlbum(Long id, AlbumDTO albumDTO) {
        // Fetch the album by ID
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        // Update album properties from the DTO
        album.setTitle(albumDTO.getTitle());
        album.setArtist(albumDTO.getArtist());
        album.setGenre(Genre.valueOf(albumDTO.getGenre()));  // Set genre from DTO
        album.setAvailable(albumDTO.isAvailable());

        // Check if store ID is provided for update
        if (albumDTO.getStoreId() != null) {
            Store store = storeRepository.findById(albumDTO.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Store not found"));
            album.setStore(store);  // Update the store association
        }

        // Save the updated album and convert it back to DTO
        album = albumRepository.save(album);
        return albumMapper.toDTO(album);
    }

    // E. Delete an album
    public void deleteAlbum(Long id) {
        // Check if album exists before attempting deletion
        if (!albumRepository.existsById(id)) {
            throw new RuntimeException("Album not found");
        }
        albumRepository.deleteById(id);
    }
}
