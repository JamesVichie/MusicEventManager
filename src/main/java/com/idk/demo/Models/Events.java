package com.idk.demo.Models;

import java.sql.Time;
import java.text.DateFormat;

public class Events {
    private int clientID;
    private String eventName;
    private String mainArtist;
    private String supportingArtist;
    private DateFormat date;
    private Time time;
    private int duration;
    private int audienceSize;
    private String eventType;
    private String category;

    public Events(int clientID, String eventName, String mainArtist, String supportingArtist,
                  DateFormat date, Time time, int duration, int audienceSize, String eventType, String category) {
        this.clientID = clientID;
        this.eventName = eventName;
        this.mainArtist = mainArtist;
        this.supportingArtist = supportingArtist;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.audienceSize = audienceSize;
        this.eventType = eventType;
        this.category = category;
    }

    public int getClientID() {
        return clientID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getMainArtist() {
        return mainArtist;
    }

    public String getSupportingArtist() {
        return supportingArtist;
    }

    public DateFormat getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public int getAudienceSize() {
        return audienceSize;
    }

    public String getEventType() {
        return eventType;
    }

    public String getCategory() {
        return category;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setMainArtist(String mainArtist) {
        this.mainArtist = mainArtist;
    }

    public void setSupportingArtist(String supportingArtist) {
        this.supportingArtist = supportingArtist;
    }

    public void setDate(DateFormat date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setAudienceSize(int audienceSize) {
        this.audienceSize = audienceSize;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
