package com.edu.eksamenbackend.enums;

public enum Role {
    ADMIN, CUSTOMER;

    public String getRoleName() {
        return "ROLE_" + this.name();
    }
}
