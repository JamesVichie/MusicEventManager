package com.idk.demo.Models;

import java.io.Serializable;

public class Clients implements Serializable {
    private int clientID;
    private String clientName;
    private float totalCommission = 0;

    public Clients(int clientID, String clientName) {
        this.clientID = clientID;
        this.clientName = clientName;
    }

    public Clients(int clientID, String clientName, Float totalCommission) {
        this.clientID = clientID;
        this.clientName = clientName;
        this.totalCommission = totalCommission;
    }

    public void setClientCommission(float totalCommission) {
        this.totalCommission = totalCommission;
    }
    public float getClientCommission() {
        return totalCommission;
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
