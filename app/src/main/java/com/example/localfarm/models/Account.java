package com.example.localfarm.models;

public class Account {

    private String email;
    private String password;

    public Account(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Account(){}

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
