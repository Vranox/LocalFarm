package com.example.myapplication.ui.order;

public class Order {
    private final String label;
    private double totalPrice;
    private final String source;
    private final String destination;
    private OrderState state;


    public Order(String label, String src, String dest, int totalPrice) {
        this.label = label;
        this.source = src;
        this.destination = dest;
        this.totalPrice = totalPrice;
        this.state = OrderState.Ongoing;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

