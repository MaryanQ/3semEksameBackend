package com.edu.eksamenbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCustomerRequest {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
}
