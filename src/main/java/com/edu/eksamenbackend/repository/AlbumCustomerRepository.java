package com.edu.eksamenbackend.repository;

import com.edu.eksamenbackend.entity.AlbumCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumCustomerRepository extends JpaRepository<AlbumCustomer, Long> {
    // Find all reserved albums for a specific customer
    List<AlbumCustomer> findByCustomerIdAndReservedTrue(Long customerId);

    // Find all reserved albums for a specific customer and only available albums
    List<AlbumCustomer> findByCustomerIdAndReservedTrueAndAlbumAvailableTrue(Long customerId);

    // Find a specific album reservation by a customer
    AlbumCustomer findByCustomerIdAndAlbumId(Long customerId, Long albumId);
}
