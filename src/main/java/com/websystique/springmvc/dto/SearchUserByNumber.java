package com.websystique.springmvc.dto;

public class SearchUserByNumber {
    private String phoneNumber;

    public SearchUserByNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SearchUserByNumber() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
