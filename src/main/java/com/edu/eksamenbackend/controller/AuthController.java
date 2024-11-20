package com.edu.eksamenbackend.controller;

import com.edu.eksamenbackend.config.JwtUtil;
import com.edu.eksamenbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        userService.registerUser(username, password);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        // Bekræft brugeren via authenticateUser
        userService.authenticateUser(username, password);

        // Generer JWT-token, hvis autentifikationen lykkes
        String token = jwtUtil.generateToken(username);

        return ResponseEntity.ok(Map.of("token", token));
    }



}