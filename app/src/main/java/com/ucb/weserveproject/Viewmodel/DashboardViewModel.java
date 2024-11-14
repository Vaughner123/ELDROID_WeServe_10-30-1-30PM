// DashboardViewModel.java
package com.ucb.weserveproject.Viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> eventName = new MutableLiveData<>();
    private final MutableLiveData<String> eventAddress = new MutableLiveData<>();
    private final MutableLiveData<String> eventDescription = new MutableLiveData<>();
    private final MutableLiveData<String> eventImageUri = new MutableLiveData<>();
    private final MutableLiveData<String> notificationText = new MutableLiveData<>();

    // Add a list of events to be observed
    private final MutableLiveData<List<Event>> eventList = new MutableLiveData<>(new ArrayList<>());

    // Method to post a new event
    public void postEvent(String name, String address, String description, String imageUri) {
        eventName.setValue(name);
        eventAddress.setValue(address);
        eventDescription.setValue(description);
        eventImageUri.setValue(imageUri);

        // Add the new event to the event list (or create a new list)
        List<Event> currentEvents = eventList.getValue();
        if (currentEvents != null) {
            currentEvents.add(new Event(name, address, description, imageUri));
            eventList.setValue(currentEvents);  // Update the list
        }
    }

    // Getter for event list
    public LiveData<List<Event>> getEventList() {
        return eventList;
    }

    // Getter and Setter for event details
    public LiveData<String> getEventName() {
        return eventName;
    }

    public LiveData<String> getEventAddress() {
        return eventAddress;
    }

    public LiveData<String> getEventDescription() {
        return eventDescription;
    }

    public LiveData<String> getEventImageUri() {
        return eventImageUri;
    }

    // Getter for notification text
    public LiveData<String> getText() {
        return notificationText;
    }

    // Setter for notification text
    public void setText(String text) {
        notificationText.setValue(text);
    }
}
