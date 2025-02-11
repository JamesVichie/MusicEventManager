package com.idk.demo.Models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Orders implements Serializable {
    private int orderID;
    private String clientName;
    private String eventName;
    private String venueName;
    private Date orderDate;
    private Time orderTime;
    private int duration;
    private float price;
    private float commission;

    public Orders(int orderID, String eventName, String venueName) {
        this.orderID = orderID;
        this.eventName = eventName;
        this.venueName = venueName;
    }
    public Orders(String eventName, String venueName) {
        this.eventName = eventName;
        this.venueName = venueName;
    }
    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getVenueName() {
        return venueName;
    }
    public void setVenueName(String venueName) {
        this.venueName = venueName;

    }
    public String getEventName() {
        return eventName;

    }
    public void setEventName(String eventName) {
        this.eventName = eventName;

    }

}


//choose a venue, choose an event,
// mash them into an order.
// set order to confirmed
//use the reuqest and venue objects to get extra info, or could just store extra here in za rams