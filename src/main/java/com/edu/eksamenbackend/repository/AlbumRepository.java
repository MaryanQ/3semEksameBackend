package com.edu.eksamenbackend.repository;

import com.edu.eksamenbackend.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
