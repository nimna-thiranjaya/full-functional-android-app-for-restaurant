package com.example.grandlegacyresturant.Model;

public class OrderModel {
    String status,add1,add2,city,district,name,oid,province,total;

    public OrderModel(String Status, String add1, String add2, String city, String district, String name, String oid, String province, String total) {
        this.status = Status;
        this.add1 = add1;
        this.add2 = add2;
        this.city = city;
        this.district = district;
        this.name = name;
        this.oid = oid;
        this.province = province;
        this.total = total;
    }

    public OrderModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
