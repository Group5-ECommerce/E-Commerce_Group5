package com.christian.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.christian.model.User;
import com.christian.service.UserService;

@RestController
public class UserRoleController {
	@Autowired
	private UserService service;

	@GetMapping("/userRoles")
	public List<User> listAllUser() {
		return service.getAllUsers();

	}

	// This shouldn't work- think about how to use composite id use cases future iterations.
	@GetMapping("/userRoles/{id}")
	public Optional<User> getUserId(@PathVariable Integer id) {
		Optional<User> user = service.getUserById(id);
		return user;
	}
	
	@PutMapping("/userRoles")
	public void updateUser(@RequestBody User newuser){
		service.updateUser(newuser);
	}

	@PostMapping("/userRoles")
	public void addUser(@RequestBody User user) {
		service.addUser(user);
	}
}
