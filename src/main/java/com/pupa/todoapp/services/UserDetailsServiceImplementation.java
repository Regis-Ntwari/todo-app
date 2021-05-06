package com.pupa.todoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pupa.todoapp.models.User;
import com.pupa.todoapp.repositories.UserRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("username not found"));
		return UserDetailsImplementation.build(user);
	}
}
