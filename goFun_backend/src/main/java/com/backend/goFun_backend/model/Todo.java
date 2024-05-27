package com.backend.goFun_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Todo {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String description;
	    private boolean completed;

	    @ManyToOne
	    @JoinColumn(name = "member_id")
	    private Member member;

	    // Getters and Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public boolean isCompleted() {
	        return completed;
	    }

	    public void setCompleted(boolean completed) {
	        this.completed = completed;
	    }

	    public Member getMember() {
	        return member;
	    }

	    public void setMember(Member member) {
	        this.member = member;
	    }
}
