package com.infosystem.springmvc.model.enums;

import java.util.Arrays;
import java.util.HashSet;

public enum TariffOptionRule {
    EXCLUDING("EXCLUDING"),
    RELATED("RELATED");

    String tariffOptionRule;

    TariffOptionRule(String tariffOptionRule) {
        this.tariffOptionRule = tariffOptionRule;
    }

    public String getRole() {
        return tariffOptionRule;
    }

    public static HashSet<Role> getAllRoles(){
        return new HashSet<>(Arrays.asList(Role.values()));
    }
}
