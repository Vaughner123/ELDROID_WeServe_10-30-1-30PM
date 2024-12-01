package com.ucb.weserveproject;

public class Event {
    private String eventName;
    private String address;
    private String description;
    private String imageUri;  // Store image URI

    public Event(String eventName, String address, String description, String imageUri) {
        this.eventName = eventName;
        this.address = address;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getEventName() {
        return eventName;
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

    public static class ProfileFragment {
    }

    public static class SettingsActivity {
    }
}
