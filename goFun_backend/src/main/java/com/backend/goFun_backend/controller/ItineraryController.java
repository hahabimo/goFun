package com.backend.goFun_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.backend.goFun_backend.dto.ItineraryDTO;
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
    public ResponseEntity<ItineraryDTO> createItinerary(@RequestBody ItineraryDTO itineraryDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Member member = memberRepository.findByEmail(currentUserName);

        Itinerary itinerary = new Itinerary();
        itinerary.setName(itineraryDTO.getName());
        itinerary.setDescription(itineraryDTO.getDescription());
        itinerary.setDays(itineraryDTO.getDays());
        
        itineraryRepository.save(itinerary);
        member.getItineraries().add(itinerary);
        memberRepository.save(member);

        return ResponseEntity.ok(itineraryToDTO(itinerary));
    }

    @GetMapping
    public ResponseEntity<?> getUserItineraries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Member member = memberRepository.findByEmail(currentUserName);

        if (member != null) {
            return ResponseEntity.ok(member.getItineraries().stream()
                    .map(this::itineraryToDTO)
                    .collect(Collectors.toList()));
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItineraryDTO> updateItinerary(@PathVariable int id, @RequestBody ItineraryDTO itineraryDTO) {
        Optional<Itinerary> itineraryOptional = itineraryRepository.findById(id);
        if (itineraryOptional.isPresent()) {
            Itinerary itinerary = itineraryOptional.get();
            itinerary.setName(itineraryDTO.getName());
            itinerary.setDescription(itineraryDTO.getDescription());
            itinerary.setDays(itineraryDTO.getDays());
            itineraryRepository.save(itinerary);
            return ResponseEntity.ok(itineraryToDTO(itinerary));
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItinerary(@PathVariable int id) {
        Optional<Itinerary> itineraryOptional = itineraryRepository.findById(id);
        if (itineraryOptional.isPresent()) {
            Itinerary itinerary = itineraryOptional.get();
            // 從所有會員中移除該行程
            Set<Member> members = itinerary.getMembers();
            for (Member member : members) {
                member.getItineraries().remove(itinerary);
                memberRepository.save(member);
            }
            itineraryRepository.delete(itinerary);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).body("Itinerary not found");
        }
    }

    @PostMapping("/addItinerary/{id}")
    public ResponseEntity<ItineraryDTO> addItinerary(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Member member = memberRepository.findByEmail(currentUserName);

        Optional<Itinerary> itineraryOptional = itineraryRepository.findById(id);
        if (itineraryOptional.isPresent()) {
            member.getItineraries().add(itineraryOptional.get());
            memberRepository.save(member);
            return ResponseEntity.ok(itineraryToDTO(itineraryOptional.get()));
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    private ItineraryDTO itineraryToDTO(Itinerary itinerary) {
        ItineraryDTO dto = new ItineraryDTO();
        dto.setId(itinerary.getId());
        dto.setName(itinerary.getName());
        dto.setDescription(itinerary.getDescription());
        dto.setDays(itinerary.getDays());
        return dto;
    }
}