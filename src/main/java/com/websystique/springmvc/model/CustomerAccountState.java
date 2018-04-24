package com.websystique.springmvc.model;
/*
* NORMAL - is not blocked
* USER_BLOCKED - blocked by user himself
* ADMIN_BLOCKED - blocked by admin/manager
* */
public enum CustomerAccountState {
    NORMAL("NORMAL"),
    USER_BLOCKED("USER_BLOCKED"),
    ADMIN_BLOCKED("ADMIN_BLOCKED");

    String customerAcctountState;

    CustomerAccountState(String userAccountState) {
        this.customerAcctountState = userAccountState;
    }

    public String getCustomerAcctountState() {
        return customerAcctountState;
    }

}
