package com.idk.demo.Models;

class VenueAvailability {
    private int bookingID;
    private int venueID;
    private String date;
    private String time;

    public VenueAvailability(int bookingID, int venueID, String date, String time) {
        this.bookingID = bookingID;
        this.venueID = venueID;
        this.date = date;
        this.time = time;
    }

    public int getBookingID() {
        return bookingID;
    }
    public int getVenueID() {
        return venueID;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
