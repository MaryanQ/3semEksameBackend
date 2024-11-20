package com.edu.eksamenbackend.config;

import com.edu.eksamenbackend.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Fjern "Bearer " fra token
            try {
                username = jwtUtil.extractUsername(jwt); // Ekstrahér brugernavn fra token
            } catch (Exception e) {
                System.out.println("JWT token is invalid: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response); // Videregiv anmodningen
    }
}