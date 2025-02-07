package com.idk.demo.Models;

public class Venues {
    private int venueID;
    private String venueName;
    private int capacity;
    private String suitable;
    private String category;
    private float price;

    public Venues(int venueID, String venueName, int capacity, String suitable, String category, float price) {
        this.venueID = venueID;
        this.venueName = venueName;
        this.capacity = capacity;
        this.suitable = suitable;
        this.category = category;
        this.price = price;
    }

    public Venues(String venueName, int capacity, String suitable, String category, float price) {
        this.venueName = venueName;
        this.capacity = capacity;
        this.suitable = suitable;
        this.category = category;
        this.price = price;
    }

    public void setVenueID(int venueID) {this.venueID = venueID;}
    public int getVenueID() {
        return venueID;
    }
    public String getVenueName() {
        return venueName;
    }
    public int getCapacity() {
        return capacity;
    }
    public String getSuitable() {
        return suitable;
    }

    public String getCategory() {
        return category;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setSuitable(String suitable) {
        this.suitable = suitable;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venueID=" + venueID +
                ", venueName='" + venueName + '\'' +
                ", capacity=" + capacity +
                ", suitable='" + suitable + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
