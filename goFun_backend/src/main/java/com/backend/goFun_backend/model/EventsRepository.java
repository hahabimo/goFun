package com.backend.goFun_backend.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EventsRepository extends CrudRepository<Events, Integer> {
	List<Events> findByItineraryId(int itineraryId);
}
