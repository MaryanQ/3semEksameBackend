package com.edu.eksamenbackend.service;

import com.edu.eksamenbackend.Mapper.AlbumMapper;
import com.edu.eksamenbackend.Mapper.StoreMapper;
import com.edu.eksamenbackend.dto.AlbumDTO;
import com.edu.eksamenbackend.dto.StoreDTO;
import com.edu.eksamenbackend.entity.Album;
import com.edu.eksamenbackend.entity.AlbumCustomer;
import com.edu.eksamenbackend.entity.Store;
import com.edu.eksamenbackend.repository.AlbumCustomerRepository;
import com.edu.eksamenbackend.repository.AlbumRepository;
import com.edu.eksamenbackend.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final AlbumCustomerRepository albumCustomerRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, AlbumRepository albumRepository, AlbumMapper albumMapper, AlbumCustomerRepository albumCustomerRepository) {
        this.storeRepository = storeRepository;
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.albumCustomerRepository = albumCustomerRepository;
    }

    // A. Assign an album to a store
    public void assignAlbumToStore(Long albumId, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        album.setStore(store);  // Set the store to the album
        albumRepository.save(album);
    }

    // B. View store details, including linked albums
    public StoreDTO getStoreDetails(Long storeId, String artist, Boolean available) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        List<Album> albums = albumRepository.findByStoreId(storeId);

        // Filtering albums based on artist and availability
        if (artist != null) {
            albums = albums.stream()
                    .filter(album -> album.getArtist().equalsIgnoreCase(artist))
                    .collect(Collectors.toList());
        }

        if (available != null) {
            albums = albums.stream()
                    .filter(album -> album.isAvailable() == available)
                    .collect(Collectors.toList());
        }

        List<AlbumDTO> albumDTOs = albums.stream()
                .map(albumMapper::toDTO)
                .collect(Collectors.toList());

        // Convert store to DTO and set linked albums
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(store.getId());
        storeDTO.setName(store.getName());
        storeDTO.setStreet(store.getStreet());
        storeDTO.setCity(store.getCity());
        storeDTO.setAlbums(albumDTOs);

        return storeDTO;
    }

    // C. Get a list of albums reserved by a customer and now available
    public List<AlbumDTO> getAvailableReservedAlbums(Long customerId) {
        // Fetch albums reserved by the customer
        List<AlbumCustomer> reservedAlbums = albumCustomerRepository.findByCustomerIdAndReservedTrue(customerId);
        // Filter by available albums
        return reservedAlbums.stream()
                .filter(albumCustomer -> albumCustomer.getAlbum().isAvailable())
                .map(albumCustomer -> albumMapper.toDTO(albumCustomer.getAlbum()))
                .collect(Collectors.toList());
    }
}