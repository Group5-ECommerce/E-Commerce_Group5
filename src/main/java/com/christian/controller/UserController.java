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
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping("/users")
	public List<User> listAllUser() {
		return service.getAllUsers();

	}

	@GetMapping("/users/{id}")
	public Optional<User> getUserId(@PathVariable Integer id) {
		Optional<User> user = service.getUserById(id);
		return user;
	}
	
	@PutMapping("/users")
	public void updateUser(@RequestBody User newuser){
		service.updateUser(newuser);
	}

	@PostMapping("/users")
	public void addUser(@RequestBody User user) {
		service.addUser(user);
	}
}
