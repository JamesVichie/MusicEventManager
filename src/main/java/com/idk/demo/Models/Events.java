package com.idk.demo.Models;

class Events {
    private int eventID;
    private int clientID;
    private String eventName;
    private String mainArtist;
    private String supportingArtist;
    private String eventType;
    private int audienceSize;
    private String category;

    public Events(int eventID, int clientID, String eventName, String mainArtist, String supportingArtist, String eventType, int audienceSize, String category) {
        this.eventID = eventID;
        this.clientID = clientID;
        this.eventName = eventName;
        this.mainArtist = mainArtist;
        this.supportingArtist = supportingArtist;
        this.eventType = eventType;
        this.audienceSize = audienceSize;
        this.category = category;
    }

    public int getEventID() {
        return eventID;
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
    public String getEventType() {
        return eventType;
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
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}

