package com.sam007.samiyal.reg_app.model;

public class data {
    private String email,name,pass;
    public data() {}

    public data(String email, String name, String pass) {
        this.email = email;
        this.pass = pass;
        this.name = name;
    }
/*
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
*/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
