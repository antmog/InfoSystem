package com.infosystem.springmvc.model;

public enum Role {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
