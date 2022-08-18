package com.christian.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String getDefaultPage() {
		return "This is the default page.";
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

	@GetMapping("/user")
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
	
	@PutMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void updateUser(@RequestBody User updatedUser){
		service.updateUser(updatedUser);
	}
	
	// Make it so the customer can only edit themself. Also can delete themself.
	@PutMapping("/userAsCustomer")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void updateUserAsCustomer(@RequestBody User updatedUser, Principal principal){
		//It is not updating, but setting a new user...
		if (principal.getName().equals(updatedUser.getUsername())) {
			service.updateUser(updatedUser);
		}
	}
	
	@DeleteMapping("/user/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void deleteUser(@PathVariable Integer id) {
		service.deleteUser(id);
	}
}
