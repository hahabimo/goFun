package com.backend.goFun_backend.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItineraryRepository extends CrudRepository<Itinerary, Integer> {

    List<Itinerary> findByMembers(Member members);
}
