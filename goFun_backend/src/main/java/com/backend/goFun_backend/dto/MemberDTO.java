package com.backend.goFun_backend.dto;

import java.util.Set;

public class MemberDTO {
    private int id;
    private String name;
    private String email;
    private String password;
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private Set<Integer> itineraryIds;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Integer> getItineraryIds() {
        return itineraryIds;
    }

    public void setItineraryIds(Set<Integer> set) {
        this.itineraryIds = set;
    }
}
