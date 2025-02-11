package com.idk.demo.Models;

import java.io.Serializable;

public class Clients implements Serializable {
    private int clientID;
    private String clientName;

    public Clients(int clientID, String clientName) {
        this.clientID = clientID;
        this.clientName = clientName;
    }
    public String getClassType() {
        return "client";
    }

    public int getClientID() {
        return clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "clientID=" + clientID +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
