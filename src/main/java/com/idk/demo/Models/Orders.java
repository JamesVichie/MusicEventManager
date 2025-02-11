package com.idk.demo.Models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

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


    public Orders(int orderID, String eventName, String venueName, Date orderDate, int duration, float price, float commission) {
        this.orderID = orderID;
        this.eventName = eventName;
        this.venueName = venueName;
        this.orderDate = orderDate;
        this.duration = duration;
        this.price = price;
        this.commission = commission;

    }

    public Orders(String eventName, String venueName, Date orderDate, int duration, float price, float commission) {
        this.eventName = eventName;
        this.venueName = venueName;
        this.orderDate = orderDate;
        this.duration = duration;
        this.price = price;
        this.commission = commission;

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
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;

    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public float getCommission() {
        return commission;
    }
    public void setCommission(float commission) {
        this.commission = commission;
    }


}


//choose a venue, choose an event,
// mash them into an order.
// set order to confirmed
//use the reuqest and venue objects to get extra info, or could just store extra here in za rams