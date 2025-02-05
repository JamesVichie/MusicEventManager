package com.idk.demo.Models;

class Venues {
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
}
