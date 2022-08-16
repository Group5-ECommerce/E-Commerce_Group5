package com.christian.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@GetMapping("/customer")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public String userPage() {
		return "Welcome to the ROLE_CUSTOMER page.";
	}
	
	@GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminPage() {
		return "Welcome to the ROLE_ADMIN page.";
	}
	
	@PostMapping("/user/{role}")
	public void addUser(@RequestBody User user, @PathVariable(required=false) Integer role) {
		service.addUser(user, role);
	}

	@GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<User> listAllUser() {
		return service.getAllUsers();
	}
	
	@GetMapping("/user/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Optional<User> getUserId(@PathVariable Integer id) {
		Optional<User> user = service.getUserById(id);
		return user;
	}
	
	@PutMapping("/users")
	//@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void updateUser(@RequestBody User newuser){
		service.updateUser(newuser);
	}
	
	@DeleteMapping("/user/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void deleteUser(@PathVariable Integer id) {
		service.deleteUser(id);
	}
}
