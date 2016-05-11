/*
    Cole Howell, Manoj Bompada
    User.java
    ITCS 4180
 */

package com.example.cole.homework8;

import java.io.Serializable;

/**
 * Created by Manoj on 4/15/2016.
 */
public class User implements Serializable {

    String fullname = "", email = "", password = "", picture = "";
    Integer phoneno = 0000000000;

    public User() {

    }

    public User(String fullname, String email, String password, String picture, Integer phoneno) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.phoneno = phoneno;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", picture='" + picture + '\'' +
                ", phoneno=" + phoneno +
                '}';
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(Integer phoneno) {
        this.phoneno = phoneno;
    }
}
