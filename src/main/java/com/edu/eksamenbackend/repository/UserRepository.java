package com.edu.eksamenbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edu.eksamenbackend.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
