package com.pupa.todoapp.models.payload;

import java.time.LocalDate;

public class TaskRequest {
	private String name;
	private LocalDate deadline;
	public TaskRequest() {
		super();
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
}
