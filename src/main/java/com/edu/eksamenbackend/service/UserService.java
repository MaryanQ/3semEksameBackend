package com.edu.eksamenbackend.service;

import com.edu.eksamenbackend.entity.Customer;
import com.edu.eksamenbackend.entity.User; // Din egen User-entitet
import com.edu.eksamenbackend.enums.Role;
import com.edu.eksamenbackend.repository.CustomerRepository;
import com.edu.eksamenbackend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet())
        );
    }

    @PostConstruct
    public void createDefaultAdmin() {
        String adminUsername = "admin";
        String adminPassword = "secureAdminPassword";

        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.addRole(Role.ADMIN);
            userRepository.save(adminUser);
            System.out.println("Admin user created: admin / secureAdminPassword");
        }
    }

    @Transactional
    public Long registerCustomer(String username, String password, String name, String email, String phoneNumber) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(Role.CUSTOMER);

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);

        // Establish bidirectional relationship
        user.setCustomer(customer);
        customer.setUser(user);

        // Save the user
        userRepository.save(user);

        // Return the customer ID
        return customer.getId();
    }



    private void validateRegistration(String username, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new EntityExistsException("User already exists");
        }
        if (customerRepository.findByEmail(email).isPresent()) {
            throw new EntityExistsException("Email already exists");
        }
    }
    private User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(Role.CUSTOMER);
        return user;
    }

    private Customer createCustomer(String name, String email, String phoneNumber, User user) {
        return new Customer(name, email, phoneNumber, user);
    }


    public void authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public Set<String> getUserRoles(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.toSet());
    }
}