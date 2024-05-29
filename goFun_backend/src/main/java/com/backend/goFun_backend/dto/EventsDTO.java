package com.backend.goFun_backend.dto;

public class EventsDTO {
	private int id;
    private String name;
    private String description;
    private int belongDay;
    private int itineraryId;
    
    public EventsDTO() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getBelongDay() {
        return belongDay;
    }

    public void setBelongDay(int belongDay) {
        this.belongDay = belongDay;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(int itineraryId) {
        this.itineraryId = itineraryId;
    }
}
