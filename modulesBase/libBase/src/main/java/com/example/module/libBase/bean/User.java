package com.example.module.libBase.bean;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String userName;

    public User() {
    }

    public User(String email, String userName) {
        this.email = email;
        this.userName = userName;
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

    @Override
    public String toString() {
        return "email='" + email + '\'' +
                        ", userName='" + userName;
    }
}
