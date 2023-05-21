package com.example.localfarm.models;
import java.util.UUID;
public class Account {

    private String email;
    private String password;
    private String phone;
    private String name;
    private String surname;
    private String id;
    private Position approxPosition;

    public Account(String email, String password, String phone, String name, String surname, String id){
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.approxPosition = new Position(43.592350475687944, 7.077720662024486, "Antibes");
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
