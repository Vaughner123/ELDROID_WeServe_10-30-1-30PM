package com.ucb.weserveproject.Viewmodel;

public class Event {
    private String name;
    private String address;
    private String description;
    private String imageUri;

    public Event(String name, String address, String description, String imageUri) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUri() {
        return imageUri;
    }
}
