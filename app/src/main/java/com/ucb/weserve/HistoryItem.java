package com.ucb.weserve;

public class HistoryItem {
    private String name;
    private String description;

    public HistoryItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
