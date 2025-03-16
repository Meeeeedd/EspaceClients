package com.example.espaceclient.model;

public class Event {
    private String image;
    private String dateLocation;
    private String title;
    private String description;

    public Event(String image, String dateLocation, String title, String description) {
        this.image = image;
        this.dateLocation = dateLocation;
        this.title = title;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getDateLocation() {
        return dateLocation;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}