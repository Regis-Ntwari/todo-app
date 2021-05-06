package com.pupa.todoapp.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pupa.todoapp.models.Task;
import com.pupa.todoapp.models.TaskStatus;
import com.pupa.todoapp.models.User;
import com.pupa.todoapp.models.payload.TaskRequest;
import com.pupa.todoapp.repositories.TaskRepository;
import com.pupa.todoapp.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tasks")
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> save(@RequestBody TaskRequest request) {
		Task task = new Task();
		task.setDeadline(request.getDeadline());
		task.setName(request.getName());

		UserDetails userLoggedIn = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> user = userRepository.findByUsername(userLoggedIn.getUsername());
		task.setUser(user.get());
		task.setStatus(TaskStatus.PENDING);
		return ResponseEntity.ok(taskRepository.save(task));
	}
	
	@GetMapping("/update")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> completeTask(@RequestParam String id){
		Task task = taskRepository.getOne(id);
		task.setStatus(TaskStatus.COMPLETED);
		task.setTimeOfCompletion(LocalDateTime.now());
		return ResponseEntity.ok(taskRepository.save(task));
	}
	@GetMapping("/my")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> findAllUserTasks(){
		UserDetails userLoggedIn = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> user = userRepository.findByUsername(userLoggedIn.getUsername());
		return ResponseEntity.ok(taskRepository.findByUser(user.get()));
	}
	@GetMapping("/approve")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> approveTask(@RequestParam String id){
		Task task = taskRepository.getOne(id);
		task.setApproved(true);
		return ResponseEntity.ok(taskRepository.save(task));
	}
	@GetMapping("/all")
	public ResponseEntity<?> findAllApprovedTasks(){
		return ResponseEntity.ok(taskRepository.findByApprovedTrue());
	}
}
