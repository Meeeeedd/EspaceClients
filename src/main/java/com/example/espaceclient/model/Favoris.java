package com.example.espaceclient.model;

import java.time.LocalDate;

public class Favoris {
    private int id;
    private String title;
    private String image;
    private LocalDate date;

    public Favoris(int id, String title, String image, LocalDate date) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public LocalDate getDate() {
        return date;
    }
}