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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // A. Assign an album to a store
    @PostMapping("/{storeId}/albums/{albumId}")
    public ResponseEntity<Void> assignAlbumToStore(@PathVariable Long storeId, @PathVariable Long albumId) {
        storeService.assignAlbumToStore(albumId, storeId);
        return ResponseEntity.ok().build();
    }

    // B. Get store details, including albums (filter by artist and availability)
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDTO> getStoreDetails(@PathVariable Long storeId,
                                                    @RequestParam(value = "artist", required = false) String artist,
                                                    @RequestParam(value = "available", required = false) Boolean available) {
        StoreDTO storeDTO = storeService.getStoreDetails(storeId, artist, available);
        return ResponseEntity.ok(storeDTO);
    }

    // C. Get a list of albums reserved by a customer that are now available
    @GetMapping("/{customerId}/available-reserved-albums")
    public ResponseEntity<List<AlbumDTO>> getAvailableReservedAlbums(@PathVariable Long customerId) {
        List<AlbumDTO> albums = storeService.getAvailableReservedAlbums(customerId);
        return ResponseEntity.ok(albums);
    }
}
