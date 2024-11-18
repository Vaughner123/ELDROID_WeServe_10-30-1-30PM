package com.ucb.weserveproject;

public class HistoryItem {

    private final String name;
    private final String activity;

    public HistoryItem(String name, String activity) {
        this.name = name;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public String getActivity() {
        return activity;
    }
}
