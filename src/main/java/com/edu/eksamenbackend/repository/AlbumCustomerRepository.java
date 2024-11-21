package com.edu.eksamenbackend.repository;

import com.edu.eksamenbackend.entity.AlbumCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumCustomerRepository extends JpaRepository<AlbumCustomer, Long> {
    AlbumCustomer findByCustomerIdAndAlbumId(Long customerId, Long albumId);
    List<AlbumCustomer> findByCustomerId(Long customerId);
    List<AlbumCustomer> findByCustomerIdAndAlbumAvailable(Long customerId, boolean available);
    List<AlbumCustomer> findByCustomerIdAndReserved(Long customerId, boolean reserved);

}
