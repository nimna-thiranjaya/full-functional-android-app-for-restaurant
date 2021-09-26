package com.example.grandlegacyresturant.Model;

public class Order {
    private String name,address;
    private float total;

    public Order() {
    }

    public Order(String name, String address, float total) {
        this.name = name;
        this.address = address;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
