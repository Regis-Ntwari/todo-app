package com.pupa.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pupa.todoapp.models.ERole;
import com.pupa.todoapp.models.Role;
import com.pupa.todoapp.models.payload.RoleRequest;
import com.pupa.todoapp.repositories.RoleRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/roles")
public class RoleController {
	@Autowired
	RoleRepository roleRepository;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addRole(@RequestBody RoleRequest request) {
		Role role = new Role();
		role.setRole(ERole.valueOf(request.getName()));
		return ResponseEntity.ok(roleRepository.save(role));
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> findAllRoles(){
		return ResponseEntity.ok(roleRepository.findAll());
	}
}
