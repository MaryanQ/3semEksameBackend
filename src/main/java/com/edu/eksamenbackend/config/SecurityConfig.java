package com.edu.eksamenbackend.config;

import com.edu.eksamenbackend.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Deaktiver CSRF for enkelhed
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll() // Tillad offentlig adgang til auth-endpoints
                        .anyRequest().authenticated() // Kræv autentifikation for andre endpoints
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS) // JWT, ingen sessioner
                )
                .httpBasic(httpBasic -> httpBasic.disable()); // Deaktiver Basic Auth
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
        return authManagerBuilder.build();
    }
}
