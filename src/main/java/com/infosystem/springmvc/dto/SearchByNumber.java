package com.infosystem.springmvc.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SearchByNumber {
    @Pattern(regexp = "\\d*")
    @Size(min = 6, max = 32)
    private String phoneNumber;

    public SearchByNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SearchByNumber() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
