package com.backend.goFun_backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Member {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String name;
	private String email;
	private String password;
	
	@ManyToMany
    @JoinTable(
      name = "member_itineraries", 
      joinColumns = @JoinColumn(name = "member_id"), 
      inverseJoinColumns = @JoinColumn(name = "itinerary_id"))
    private Set<Itinerary> itineraries = new HashSet<>();
	
	public Set<Itinerary> getItineraries() {
		return itineraries;
	}
	public void setItineraries(Set<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
