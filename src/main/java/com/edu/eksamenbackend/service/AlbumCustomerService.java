package com.edu.eksamenbackend.service;

import com.edu.eksamenbackend.Mapper.AlbumCustomerMapper;
import com.edu.eksamenbackend.Mapper.AlbumMapper;
import com.edu.eksamenbackend.dto.AlbumCustomerDTO;
import com.edu.eksamenbackend.dto.AlbumDTO;
import com.edu.eksamenbackend.entity.Album;
import com.edu.eksamenbackend.entity.AlbumCustomer;
import com.edu.eksamenbackend.entity.Customer;
import com.edu.eksamenbackend.repository.AlbumCustomerRepository;
import com.edu.eksamenbackend.repository.AlbumRepository;
import com.edu.eksamenbackend.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumCustomerService {

    private final AlbumCustomerRepository albumCustomerRepository;
    private final AlbumRepository albumRepository;
    private final CustomerRepository customerRepository;
    private final AlbumCustomerMapper albumCustomerMapper;

    @Autowired
    public AlbumCustomerService(AlbumCustomerRepository albumCustomerRepository,
                                AlbumRepository albumRepository,
                                CustomerRepository customerRepository,
                                AlbumCustomerMapper albumCustomerMapper) {
        this.albumCustomerRepository = albumCustomerRepository;
        this.albumRepository = albumRepository;
        this.customerRepository = customerRepository;
        this.albumCustomerMapper = albumCustomerMapper;
    }

    // A. Register interest (reserve/like) for an album
    public void registerInterest(Long customerId, Long albumId) {
        // Find the customer and album
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Check if the customer is already registered for the album
        AlbumCustomer albumCustomer = albumCustomerRepository.findByCustomerIdAndAlbumId(customerId, albumId);
        if (albumCustomer == null) {
            albumCustomer = new AlbumCustomer();
            albumCustomer.setAlbum(album);
            albumCustomer.setCustomer(customer);
            albumCustomer.setInterestDate(java.time.LocalDate.now());
            albumCustomer.setReserved(true); // Mark the album as reserved
        } else {
            albumCustomer.setReserved(true); // Update the reservation status
        }

        albumCustomerRepository.save(albumCustomer);
    }

    // B. Unsubscribe (unreserve/unlike) from an album
    public void unsubscribeFromAlbum(Long customerId, Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        AlbumCustomer albumCustomer = albumCustomerRepository.findByCustomerIdAndAlbumId(customerId, albumId);
        if (albumCustomer != null) {
            albumCustomer.setReserved(false); // Unmark as reserved
            albumCustomer.setLiked(false); // Unmark as liked
            albumCustomerRepository.save(albumCustomer);
        } else {
            throw new RuntimeException("Customer has not shown interest in this album");
        }
    }

    // C. Get reservations (all or available albums only)
    public List<AlbumCustomerDTO> getReservations(Long customerId, boolean available) {
        List<AlbumCustomer> reservations;
        if (available) {
            reservations = albumCustomerRepository.findByCustomerIdAndReservedTrueAndAlbumAvailableTrue(customerId);
        } else {
            reservations = albumCustomerRepository.findByCustomerIdAndReservedTrue(customerId);
        }

        return reservations.stream()
                .map(albumCustomerMapper::toDTO) // Convert entities to DTOs
                .collect(Collectors.toList());
    }
}