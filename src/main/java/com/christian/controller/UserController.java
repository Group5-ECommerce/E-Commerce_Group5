package com.christian.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	
	@GetMapping("/")
	public String getError() {
		return "Error: this user probably doesn't have any roles.";
	}
	
	@GetMapping("/users")
    // @PreAuthorize("hasAuthority('ADMIN')")
	public List<User> listAllUser() {
		return service.getAllUsers();
	}
	
	@GetMapping("/user")
    // @PreAuthorize("hasAuthority('USER')")
	public String userPage() {
		return "Welcome to the USER page.";
	}
	
	@PostMapping("/test")
	public String test(@RequestBody User user) {
		return "working";
	}
	
	@PostMapping("/register")
	public void addUser(@RequestBody User user) {
		service.addUser(user);
	}
	
	@PutMapping("/users")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public void updateUser(@RequestBody User newuser){
		service.updateUser(newuser);
	}
	
	
	@GetMapping("/admin")
    // @PreAuthorize("hasAuthority('ADMIN')")
	public String adminPage() {
		return "Welcome to the ADMIN page.";

	}

	@GetMapping("/users/{id}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Optional<User> getUserId(@PathVariable Integer id) {
		Optional<User> user = service.getUserById(id);
		return user;
	}
}
