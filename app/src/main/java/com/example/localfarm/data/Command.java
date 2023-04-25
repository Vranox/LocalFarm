package com.example.localfarm.data;

public class Command {
    private Client buyer;
    private Productor seller;

    public Command(Client buyer, Productor seller){

        this.buyer = buyer;
        this.seller = seller;
    }
}
