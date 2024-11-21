package com.edu.eksamenbackend.service;

import com.edu.eksamenbackend.Mapper.AlbumMapper;
import com.edu.eksamenbackend.Mapper.StoreMapper;
import com.edu.eksamenbackend.dto.AlbumDTO;
import com.edu.eksamenbackend.dto.StoreDTO;
import com.edu.eksamenbackend.entity.Album;
import com.edu.eksamenbackend.entity.AlbumCustomer;
import com.edu.eksamenbackend.entity.Customer;
import com.edu.eksamenbackend.entity.Store;
import com.edu.eksamenbackend.repository.AlbumCustomerRepository;
import com.edu.eksamenbackend.repository.AlbumRepository;
import com.edu.eksamenbackend.repository.CustomerRepository;
import com.edu.eksamenbackend.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final AlbumCustomerRepository albumCustomerRepository;
    private final StoreMapper storeMapper;
    private CustomerRepository customerRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, AlbumRepository albumRepository, AlbumMapper albumMapper, AlbumCustomerRepository albumCustomerRepository, StoreMapper storeMapper, CustomerRepository customerRepository) {
        this.storeRepository = storeRepository;
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.albumCustomerRepository = albumCustomerRepository;
        this.storeMapper = storeMapper;
        this.customerRepository = customerRepository;
    }


    public List<StoreDTO> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(storeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public String assignAlbumToStore(Long storeId, Long albumId) {
        Optional<Store> storeOpt = storeRepository.findById(storeId);
        Optional<Album> albumOpt = albumRepository.findById(albumId);

        if (storeOpt.isEmpty()) {
            return "Store with ID " + storeId + " does not exist.";
        }
        if (albumOpt.isEmpty()) {
            return "Album with ID " + albumId + " does not exist.";
        }

        Album album = albumOpt.get();
        Store store = storeOpt.get();

        album.setStore(store);
        albumRepository.save(album);

        return "Album '" + album.getTitle() + "' assigned to store '" + store.getName() + "' successfully!";
    }


    public Map<String, Object> getStoreDetails(Long storeId, String artist, Boolean available) {
        Optional<Store> storeOpt = storeRepository.findById(storeId);
        if (storeOpt.isEmpty()) {
            return Map.of("error", "Store with ID " + storeId + " does not exist.");
        }

        Store store = storeOpt.get();
        List<Album> filteredAlbums = store.getAlbums().stream()
                .filter(album -> (artist == null || album.getArtist().equalsIgnoreCase(artist)) &&
                        (available == null || album.isAvailable() == available))
                .toList();

        return Map.of(
                "store", store,
                "albums", filteredAlbums
        );
    }

    public List<Album> getAvailableReservedAlbums(Long customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if customer doesn't exist
        }

        Customer customer = customerOpt.get();
        return albumCustomerRepository.findByCustomerIdAndReserved(customerId, true).stream()
                .map(AlbumCustomer::getAlbum)
                .filter(Album::isAvailable)
                .toList();
    }




}