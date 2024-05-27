package com.backend.goFun_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.goFun_backend.model.Event;
import com.backend.goFun_backend.model.EventRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    @GetMapping("/itinerary/{itineraryId}")
    public List<Event> getItineraryEvents(@PathVariable Long itineraryId) {
        return eventRepository.findByItineraryId(itineraryId);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        Event event = eventRepository.findById(id).orElseThrow();
        
        event.setName(eventDetails.getName());
        event.setDescription(eventDetails.getDescription());
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setItinerary(eventDetails.getItinerary());

        return eventRepository.save(event);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteEvent(@PathVariable Long id) {
        Event event = eventRepository.findById(id).orElseThrow();

        eventRepository.delete(event);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}