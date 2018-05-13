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

    public String getRule() {
        return tariffOptionRule;
    }

    public static HashSet<TariffOptionRule> getAllRules(){
        return new HashSet<>(Arrays.asList(TariffOptionRule.values()));
    }
}
