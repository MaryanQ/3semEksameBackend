package com.edu.eksamenbackend.repository;

import com.edu.eksamenbackend.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByStoreId(Long storeId);
}
