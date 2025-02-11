package com.idk.demo.Models;

import java.io.Serializable;

public class Users implements Serializable {
    private int userID;
    private String username;
    private String password;
    private String position;

    public Users(int userID, String username, String password, String position) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.position = position;
    }
    public String getClassType() {
        return "user";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "User{userID=" + userID + ", username='" + username + "', position='" + position + "'}";
    }
}
