package com.websystique.springmvc.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    BLOCKED("BLOCKED");

    String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
