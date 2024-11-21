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

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final StoreRepository storeRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, AlbumMapper albumMapper, StoreRepository storeRepository) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.storeRepository = storeRepository;
    }

    // A. Create a new album
    public AlbumDTO createAlbum(AlbumDTO albumDTO) {
        // Find butikker med navnet
        List<Store> stores = storeRepository.findByName(albumDTO.getStoreName());

        if (stores.isEmpty()) {
            throw new RuntimeException("Store not found: " + albumDTO.getStoreName());
        }

        // Automatisk vælg den første butik, hvis der findes flere
        Store store = stores.get(0);

        // Opret albummet
        Album album = new Album(albumDTO.getTitle(), albumDTO.getArtist(), albumDTO.getGenre(), albumDTO.isAvailable(), store);

        // Gem albummet i databasen
        album = albumRepository.save(album);

        // Returnér som DTO
        return albumMapper.toDTO(album);
    }



    // B. Get all albums
    public List<AlbumDTO> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream()
                .map(album -> {
                    AlbumDTO dto = albumMapper.toDTO(album);
                    if (album.getStore() != null) {
                        dto.setStoreName(album.getStore().getName()); // Set storeName in the DTO
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
            albumDTO.setStoreName(album.getStore().getName()); // Set storeName in the DTO
        }
        return albumDTO;
    }

    // D. Update an existing album
    public AlbumDTO updateAlbum(Long id, AlbumDTO albumDTO) {
        // Find albummet
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found with ID: " + id));

        // Opdater album-egenskaber
        album.setTitle(albumDTO.getTitle());
        album.setArtist(albumDTO.getArtist());
        album.setGenre(albumDTO.getGenre());
        album.setAvailable(albumDTO.isAvailable());

        // Brug storeName til at finde en butik
        if (albumDTO.getStoreName() != null) {
            List<Store> stores = storeRepository.findByName(albumDTO.getStoreName());

            if (stores.isEmpty()) {
                throw new RuntimeException("Store not found with name: " + albumDTO.getStoreName());
            }

            // Vælg den første butik fra listen
            Store store = stores.get(0);
            album.setStore(store);
        }

        // Gem albummet
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
