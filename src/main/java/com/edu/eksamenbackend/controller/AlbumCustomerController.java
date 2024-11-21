package com.edu.eksamenbackend.controller;

import com.edu.eksamenbackend.dto.AlbumCustomerDTO;
import com.edu.eksamenbackend.entity.AlbumCustomer;
import com.edu.eksamenbackend.repository.AlbumCustomerRepository;
import com.edu.eksamenbackend.service.AlbumCustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers/{customerId}/albums")
public class AlbumCustomerController {

    private final AlbumCustomerService albumCustomerService;

    @Autowired
    public AlbumCustomerController(AlbumCustomerService albumCustomerService) {
        this.albumCustomerService = albumCustomerService;
    }

    // A. Register interest
    @PostMapping("/{albumId}/reserve")
    public ResponseEntity<?> registerInterest(@PathVariable Long customerId, @PathVariable Long albumId) {
        String result = albumCustomerService.registerInterest(customerId, albumId);
        return ResponseEntity.ok(Map.of("message", result));
    }

    // B. Unsubscribe from an album
    @DeleteMapping("/{albumId}/unsubscribe")
    public ResponseEntity<?> unsubscribeInterest(@PathVariable Long customerId, @PathVariable Long albumId) {
        String result = albumCustomerService.unsubscribeInterest(customerId, albumId);
        return ResponseEntity.ok(Map.of("message", result));
    }

    // C. Get reservations
    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations(@PathVariable Long customerId,
                                             @RequestParam(defaultValue = "false") boolean availableOnly) {
        List<AlbumCustomer> reservations = albumCustomerService.getReservations(customerId, availableOnly);
        if (reservations.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No reservations found."));
        }

        return ResponseEntity.ok(reservations);
    }
}
