package com.example.localfarm.data;

import java.util.ArrayList;

public class Client {
    private String name;
    private String surname;
    private ArrayList<Command> commands = new ArrayList<>();

    public Client(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public Client(Client client) {
        name = client.name;
        surname = client.surname;
        commands = client.commands;
    }
}
