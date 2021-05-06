package com.pupa.todoapp.models.payload;

public class RoleRequest {
	private String name;

	public RoleRequest() {
		super();
	}

	public RoleRequest(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
