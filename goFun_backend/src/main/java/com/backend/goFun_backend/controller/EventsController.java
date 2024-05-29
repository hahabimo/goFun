package com.backend.goFun_backend.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.goFun_backend.dto.EventsDTO;
import com.backend.goFun_backend.model.Events;
import com.backend.goFun_backend.model.EventsRepository;
import com.backend.goFun_backend.model.ItineraryRepository;
import com.backend.goFun_backend.model.Itinerary;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventsController {
	@Autowired
	private EventsRepository eventsRepository;

	@Autowired
	private ItineraryRepository itineraryRepository;

	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody EventsDTO eventsDTO) {

		System.out.println("Received eventDTO: " + eventsDTO);
		System.out.println("Name: " + eventsDTO.getName());
		System.out.println("Description: " + eventsDTO.getDescription());
		System.out.println("BelongDay: " + eventsDTO.getBelongDay());
		System.out.println("ItineraryId: " + eventsDTO.getItineraryId());

		Optional<Itinerary> itineraryOptional = itineraryRepository.findById(eventsDTO.getItineraryId());

		if (itineraryOptional.isPresent()) {
			Itinerary itinerary = itineraryOptional.get();

			Events event = new Events();
			event.setName(eventsDTO.getName());
			event.setDescription(eventsDTO.getDescription());
			event.setBelongDay(eventsDTO.getBelongDay());
			event.setItinerary(itinerary);
			Events savedEvent = eventsRepository.save(event);

	        EventsDTO savedEventDTO = eventToDTO(savedEvent); // 將保存的事件轉換為DTO
	        return ResponseEntity.ok(savedEventDTO);
		} else {
			return ResponseEntity.ok().build();
		}
	}

	@GetMapping("/itinerary/{itineraryId}")
	public ResponseEntity<List<Events>> getItineraryEvents(@PathVariable int itineraryId) {
		List<Events> events = eventsRepository.findByItineraryId(itineraryId);
		System.out.println(events);
		return ResponseEntity.ok(events);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEvent(@PathVariable int id, @RequestBody EventsDTO eventDTO) {
		Optional<Events> eventOptional = eventsRepository.findById(id);
		if (eventOptional.isPresent()) {
			Events event = eventOptional.get();
			event.setName(eventDTO.getName());
			event.setDescription(eventDTO.getDescription());
			event.setBelongDay(eventDTO.getBelongDay());
			eventsRepository.save(event);
			return ResponseEntity.ok(event);
		} else {
			return ResponseEntity.status(404).body("Event not found");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEvent(@PathVariable int id) {
		Optional<Events> eventOptional = eventsRepository.findById(id);
		if (eventOptional.isPresent()) {
			eventsRepository.delete(eventOptional.get());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(404).body("Event not found");
		}
	}

	private EventsDTO eventToDTO(Events event) {
		EventsDTO dto = new EventsDTO();
		dto.setId(event.getId());
		dto.setName(event.getName());
		dto.setDescription(event.getDescription());
		dto.setBelongDay(event.getBelongDay());
		dto.setItineraryId(event.getItinerary().getId());
		return dto;
	}
}