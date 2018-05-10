package com.infosystem.springmvc.dto;

public class SearchByNumber {
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
