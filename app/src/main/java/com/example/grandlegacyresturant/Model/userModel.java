package com.example.grandlegacyresturant.Model;

public class userModel {

    String uname, ufeedback, urateCount;
    userModel()
    {

    }

    public userModel(String uname, String ufeedback, String urateCount) {
        this.uname = uname;
        this.ufeedback = ufeedback;
        this.urateCount = urateCount;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUfeedback() {
        return ufeedback;
    }

    public void setUfeedback(String ufeedback) {
        this.ufeedback = ufeedback;
    }

    public String getUrateCount() {
        return urateCount;
    }

    public void setUrateCount(String urateCount) {
        this.urateCount = urateCount;
    }
}


