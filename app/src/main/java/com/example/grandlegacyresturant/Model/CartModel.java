package com.example.grandlegacyresturant.Model;

public class CartModel {
    private String pid, name, price,quantity;

    public CartModel() {
    }

    public CartModel(String pid, String name, String price, String quantity) {
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
