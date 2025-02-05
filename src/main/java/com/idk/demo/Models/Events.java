package com.idk.demo.Models;

import java.sql.Time;
import java.text.DateFormat;

public class Events {
    private String clientName;
    private String eventName;
    private String mainArtist;
    private String date;
    private String time;
    private int duration;
    private int audienceSize;
    private String type;
    private String category;

    // Constructor
    public Events(String clientName, String eventName, String mainArtist, String date, String time, int duration, int audienceSize, String type, String category) {
        this.clientName = clientName;
        this.eventName = eventName;
        this.mainArtist = mainArtist;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.audienceSize = audienceSize;
        this.type = type;
        this.category = category;
    }

    // Getters
    public String getClientName() {
        return clientName;
    }

    public String getEventName() {
        return eventName;
    }

    public String getMainArtist() {
        return mainArtist;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public int getAudienceSize() {
        return audienceSize;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setMainArtist(String mainArtist) {
        this.mainArtist = mainArtist;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setAudienceSize(int audienceSize) {
        this.audienceSize = audienceSize;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    @Override
    public String toString() {
        return "Event{" +
                "clientName='" + clientName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", mainArtist='" + mainArtist + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", duration=" + duration +
                ", audienceSize=" + audienceSize +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
