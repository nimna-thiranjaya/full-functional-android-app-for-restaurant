package com.example.grandlegacyresturant.Model;

public class FoodModel {

    String name, price, food_description, surl,pid;

    public FoodModel() {

    }

    public FoodModel(String name, String price, String food_description, String surl,String pid) {
        this.name = name;
        this.price = price;
        this.food_description = food_description;
        this.surl = surl;
        this.pid =pid;
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

    public String getFood_description() {
        return food_description;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
    }

    public String getSurl() {
        return surl;
    }


    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}