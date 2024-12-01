package com.ucb.weserveproject;

public class PostData {
    private String eventName;
    private String address;
    private String description;
    private String imageUri;


    public PostData(String eventName, String address, String description, String imageUri) {
        this.eventName = eventName;
        this.address = address;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

