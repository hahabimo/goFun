package com.backend.goFun_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.goFun_backend.model.Itinerary;
import com.backend.goFun_backend.model.ItineraryRepository;
import com.backend.goFun_backend.model.Member;
import com.backend.goFun_backend.model.MemberRepository;

@RestController
@RequestMapping("/itineraries")
@CrossOrigin(origins = "http://localhost:4200")
public class ItineraryController {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<?> createItinerary(@RequestBody Itinerary itinerary) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Member member = memberRepository.findByEmail(currentUserName);

        if (member != null) {
        	itinerary.setMember(member);
        	itineraryRepository.save(itinerary);
            return ResponseEntity.ok(itinerary);
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }
    

    @GetMapping
    public ResponseEntity<?> getUserItineraries() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Member member = memberRepository.findByEmail(currentUserName);

        if (member != null) {
            List<Itinerary> itinerary = itineraryRepository.findByMember(member);
            return ResponseEntity.ok(itinerary);
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItinerary(@PathVariable int id, @RequestBody Itinerary itineraryDetails) {
    	Optional<Itinerary> itineraryOptional = itineraryRepository.findById(id);
    	if (itineraryOptional.isPresent()) {
    		Itinerary itinerary = itineraryOptional.get();
    		itinerary.setDescription(itineraryDetails.getDescription());
    		itinerary.setName(itineraryDetails.getName());
    		itinerary.setDays(itineraryDetails.getDays());
    		itineraryRepository.save(itinerary);
            return ResponseEntity.ok(itinerary);
        } else {
            return ResponseEntity.status(404).body("Itinerary not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItinerary(@PathVariable int id) {
    	Optional<Itinerary> itineraryOptional = itineraryRepository.findById(id);

    	if (itineraryOptional.isPresent()) {
    		itineraryRepository.delete(itineraryOptional.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).body("Itinerary not found");
        }
    }
    
    @PostMapping("/addItinerary/{id}")
    public ResponseEntity<?> addItinerary(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Member member = memberRepository.findByEmail(currentUserName);

        Optional<Itinerary> itinerary = itineraryRepository.findById(id);
        if (itinerary != null) {
        	member.getItineraries().add(itinerary.get());
        	memberRepository.save(member);
            return ResponseEntity.ok(itinerary);
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }
}
