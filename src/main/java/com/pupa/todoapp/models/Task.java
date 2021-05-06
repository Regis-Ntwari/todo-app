package com.pupa.todoapp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Task {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	private String name;
	private LocalDate deadline;
	private LocalDateTime timeOfCompletion;
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	@ManyToOne
	@JsonBackReference
	private User user;
	private boolean approved = false;

	public Task( String name, LocalDate deadline, LocalDateTime timeOfCompletion, TaskStatus status) {
		super();
		this.name = name;
		this.deadline = deadline;
		this.timeOfCompletion = timeOfCompletion;
		this.status = status;
	}

	public Task() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public LocalDateTime getTimeOfCompletion() {
		return timeOfCompletion;
	}

	public void setTimeOfCompletion(LocalDateTime timeOfCompletion) {
		this.timeOfCompletion = timeOfCompletion;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
}
