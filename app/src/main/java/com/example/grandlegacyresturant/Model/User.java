package com.example.grandlegacyresturant.Model;



public class User {
    private String fname,lname,email,userName,Pno, password;


    public User() {
    }

    public User(String fname, String lname, String email, String userName, String Pno, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.userName = userName;
        this.Pno= Pno;
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPno() {return Pno; }

    public void setPno(String Pno) {
        this.Pno = Pno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
