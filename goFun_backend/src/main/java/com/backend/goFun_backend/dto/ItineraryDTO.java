package com.backend.goFun_backend.dto;

public class ItineraryDTO {
    private int id;
    private String name;
    private String description;
    private int days;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int long1) {
        this.id = long1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
