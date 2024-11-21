package com.edu.eksamenbackend.controller;

import com.edu.eksamenbackend.Mapper.AlbumMapper;
import com.edu.eksamenbackend.Mapper.StoreMapper;
import com.edu.eksamenbackend.dto.AlbumDTO;
import com.edu.eksamenbackend.dto.StoreDTO;
import com.edu.eksamenbackend.entity.Album;
import com.edu.eksamenbackend.entity.Store;
import com.edu.eksamenbackend.repository.AlbumCustomerRepository;
import com.edu.eksamenbackend.repository.StoreRepository;
import com.edu.eksamenbackend.service.AlbumCustomerService;
import com.edu.eksamenbackend.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;
    private final StoreRepository storeRepository;
    private final AlbumCustomerService albumCustomerService;


    @Autowired
    public StoreController(StoreService storeService, StoreRepository storeRepository, AlbumCustomerService albumCustomerService) {
        this.storeService = storeService;
        this.storeRepository = storeRepository;
        this.albumCustomerService = albumCustomerService;
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        List<StoreDTO> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    // A. Assign an album to a store
    @PostMapping("/{storeId}/albums/{albumId}/assign")
    public ResponseEntity<?> assignAlbumToStore(@PathVariable Long storeId, @PathVariable Long albumId) {
        String result = storeService.assignAlbumToStore(storeId, albumId);
        return ResponseEntity.ok(Map.of("message", result));
    }


    // B. Get store details, including albums (filter by artist and availability)
    @GetMapping("/{storeId}")
    public ResponseEntity<?> getStoreDetails(
            @PathVariable Long storeId,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) Boolean available) {
        Map<String, Object> result = storeService.getStoreDetails(storeId, artist, available);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/customers/{customerId}/albums/available-reserved")
    public ResponseEntity<?> getAvailableReservedAlbums(@PathVariable Long customerId) {
        List<Album> albums = albumCustomerService.getAvailableReservedAlbums(customerId);
        if (albums.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No available reserved albums found for the customer."));
        }
        return ResponseEntity.ok(albums);
    }


}
