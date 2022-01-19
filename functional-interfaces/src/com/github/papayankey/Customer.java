package com.github.papayankey;

public class Customer {
    private String accountNumber;
    private String email;

    public Customer(String accountNumber, String email) {
        this.accountNumber = accountNumber;
        this.email = email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
