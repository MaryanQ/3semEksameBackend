package com.edu.eksamenbackend.dto;

import lombok.Setter;

import java.util.List;


public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Long> albumIds; // Albummets ID'er kunden er interesseret i

    // Getters, Setters, Constructors
}
