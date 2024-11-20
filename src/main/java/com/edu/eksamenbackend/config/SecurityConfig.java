package com.edu.eksamenbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails guest = User
                .withUsername("guest")
                .password(passwordEncoder().encode("guestpassword"))
                .roles("GUEST")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("adminpassword"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(guest, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Deaktiver CSRF for simplicity (ikke anbefalet til produktion)
                .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN") // Kun admin kan tilgå /admin
                .requestMatchers("/guest/**").hasRole("GUEST") // Kun gæster kan tilgå /guest
                .anyRequest().authenticated() // Alle andre endpoints kræver login
                .and()
                .formLogin() // Standard login-side
                .and()
                .httpBasic(); // Tillad basic authentication (fx med Postman)

        return http.build();
    }
}