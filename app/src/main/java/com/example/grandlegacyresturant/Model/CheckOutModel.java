package com.example.grandlegacyresturant.Model;

public class CheckOutModel {

    String name, add1, add2, city, district, province;

    CheckOutModel() {

    }


    public CheckOutModel(String name, String add1, String add2, String city, String district, String province) {
        this.name = name;
        this.add1 = add1;
        this.add2 = add2;
        this.city = city;
        this.district = district;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}