package com.idk.demo.Models;

class Clients {
    private int clientID;
    private String clientName;

    public Clients(int clientID, String clientName) {
        this.clientID = clientID;
        this.clientName = clientName;
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
}
