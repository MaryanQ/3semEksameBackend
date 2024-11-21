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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    @Transactional
    public String registerInterest(Long customerId, Long albumId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return "Customer not found with ID: " + customerId;
        }

        Optional<Album> albumOpt = albumRepository.findById(albumId);
        if (albumOpt.isEmpty()) {
            return "Album not found with ID: " + albumId;
        }

        // Check if interest already exists
        if (albumCustomerRepository.findByCustomerIdAndAlbumId(customerId, albumId) != null) {
            return "Customer has already registered interest in this album.";
        }

        // Register interest
        AlbumCustomer interest = new AlbumCustomer();
        interest.setCustomer(customerOpt.get());
        interest.setAlbum(albumOpt.get());
        interest.setInterestDate(LocalDate.now());
        interest.setReserved(true);

        albumCustomerRepository.save(interest);
        return "Interest registered successfully!";
    }


    @Transactional
    public String unsubscribeInterest(Long customerId, Long albumId) {
        AlbumCustomer interest = albumCustomerRepository.findByCustomerIdAndAlbumId(customerId, albumId);
        if (interest == null) {
            return "No existing interest found for this album and customer.";
        }

        albumCustomerRepository.delete(interest);
        return "Unsubscribed from album successfully!";
    }


    public List<AlbumCustomer> getReservations(Long customerId, boolean availableOnly) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return Collections.emptyList(); // Return an empty list
        }

        if (availableOnly) {
            return albumCustomerRepository.findByCustomerIdAndAlbumAvailable(customerId, true);
        } else {
            return albumCustomerRepository.findByCustomerId(customerId);
        }
    }

    public List<Album> getAvailableReservedAlbums(Long customerId) {
        // Check if the customer exists
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return Collections.emptyList(); // Return empty list if customer doesn't exist
        }

        // Fetch reserved albums for the customer and filter those that are available
        return albumCustomerRepository.findByCustomerIdAndReserved(customerId, true).stream()
                .map(AlbumCustomer::getAlbum)
                .filter(Album::isAvailable)
                .toList();
    }

}