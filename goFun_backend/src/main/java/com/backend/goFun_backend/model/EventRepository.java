package com.backend.goFun_backend.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByItineraryId(Long itineraryId);
}
