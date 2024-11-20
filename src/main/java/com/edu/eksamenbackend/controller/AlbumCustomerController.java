package com.edu.eksamenbackend.controller;

import com.edu.eksamenbackend.dto.AlbumCustomerDTO;
import com.edu.eksamenbackend.service.AlbumCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/albums")
public class AlbumCustomerController {

    private final AlbumCustomerService albumCustomerService;

    @Autowired
    public AlbumCustomerController(AlbumCustomerService albumCustomerService) {
        this.albumCustomerService = albumCustomerService;
    }

    // A. Register interest (reserve/like)
    @PostMapping("/{albumId}/reserve")
    public ResponseEntity<Void> registerInterest(@PathVariable Long customerId, @PathVariable Long albumId) {
        albumCustomerService.registerInterest(customerId, albumId);
        return ResponseEntity.ok().build();
    }

    // B. Unsubscribe from an album (unreserve/unlike)
    @DeleteMapping("/{albumId}/unsubscribe")
    public ResponseEntity<Void> unsubscribeFromAlbum(@PathVariable Long customerId, @PathVariable Long albumId) {
        albumCustomerService.unsubscribeFromAlbum(customerId, albumId);
        return ResponseEntity.ok().build();
    }

    // C. Get reservations (either all reservations or only available albums)
    @GetMapping("/reservations")
    public ResponseEntity<List<AlbumCustomerDTO>> getReservations(
            @PathVariable Long customerId,
            @RequestParam(value = "available", required = false, defaultValue = "false") boolean available) {
        List<AlbumCustomerDTO> reservations = albumCustomerService.getReservations(customerId, available);
        return ResponseEntity.ok(reservations);
    }
}