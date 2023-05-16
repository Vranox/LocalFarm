package com.example.localfarm.models;
import java.util.UUID;
public class Account {

    private String email;
    private String password;
    private String phone;
    private String name;
    private String id;

    public Account(String email, String password, String phone, String name, String id){
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.id = id;
    }

    public Account(){}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

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

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
