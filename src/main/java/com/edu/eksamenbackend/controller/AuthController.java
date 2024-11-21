package com.edu.eksamenbackend.controller;

import com.edu.eksamenbackend.config.JwtUtil;
import com.edu.eksamenbackend.dto.CustomerDTO;
import com.edu.eksamenbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // Register CUSTOMER
    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        try {
            userService.registerCustomer(
                    customerDTO.getUsername(),
                    customerDTO.getPassword(),
                    customerDTO.getName(),
                    customerDTO.getEmail(),
                    customerDTO.getPhoneNumber()
            );
            return ResponseEntity.ok(Map.of("message", "Customer registered successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        userService.authenticateUser(username, password); // Validates credentials
        Set<String> roles = userService.getUserRoles(username);

        String token = jwtUtil.generateToken(username, roles); // Generates JWT token

        return ResponseEntity.ok(Map.of("token", token, "roles", roles));
    }

}
