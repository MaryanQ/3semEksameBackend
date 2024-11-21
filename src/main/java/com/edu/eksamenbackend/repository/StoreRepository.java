package com.edu.eksamenbackend.repository;

import com.edu.eksamenbackend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByName(String name); }
