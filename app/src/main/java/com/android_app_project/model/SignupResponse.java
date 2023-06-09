package com.android_app_project.model;


import com.android_app_project.entities.Account;
import com.android_app_project.entities.Customer;

import java.io.Serializable;

public class SignupResponse implements Serializable {
    String message;
    Account account;
    Customer customer;
    public SignupResponse(String message,Account account,Customer customer) {
        this.message = message;
        this.account = account;
        this.customer = customer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
