package com.pupa.todoapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pupa.todoapp.models.Task;
import com.pupa.todoapp.models.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
	public List<Task> findByUser(User user);
	public List<Task> findByApprovedTrue();
}
