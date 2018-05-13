package com.infosystem.springmvc.model.enums;

import java.util.Arrays;
import java.util.HashSet;

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

    public static HashSet<Role> getAllRoles(){
        return new HashSet<>(Arrays.asList(Role.values()));
    }
}
