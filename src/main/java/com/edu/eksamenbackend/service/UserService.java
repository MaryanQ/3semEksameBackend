package com.edu.eksamenbackend.service;

import com.edu.eksamenbackend.entity.User; // Din egen User-entitet
import com.edu.eksamenbackend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User( // Spring Security User
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(new BCryptPasswordEncoder().encode(password)); // Hash adgangskoden

        return userRepository.save(newUser);
    }



    public void authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("User not found for username: " + username);
                    return new RuntimeException("Invalid username or password");
                });

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            System.out.println("Password does not match for username: " + username);
            throw new RuntimeException("Invalid username or password");
        }
        System.out.println("User authenticated successfully: " + username);
    }


}