package com.hcl.controller;

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

import com.hcl.entity.User;
import com.hcl.service.UserService;

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
	/*
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
	public Optional<User> getUserById(@PathVariable Integer id) {
		Optional<User> user = service.getUserById(id);
		return user;
	}
	
	//Make sure this accounts for userIds. Admin has to send an id with the request, unlike customer who wouldn't know.
	@PutMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void updateUser(@RequestBody User updatedUser){
		service.saveUser(updatedUser);
	}
	
	// Lets the user completely update their database entry. 
	// This isn't traditionally done on a website, but I included it for completion sake.
	@PutMapping("/changeAllDetails")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void updateUserAsUser(@RequestBody User updatedUser, Principal principal){
		Optional<User> user = service.getUserByUsername(principal.getName());
		if (user.isPresent())
		{
			// Set the updated user's id to the current entry's id.
			// This is more efficient than copying all of the updated properties to the current user.
			updatedUser.setUserId(user.get().getUserId());
			service.saveUser(updatedUser);
		}
	}
	
	@PutMapping("/changeUsername")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void changeUsername(@RequestBody User updatedUser, Principal principal){
		Optional<User> user = service.getUserByUsername(principal.getName());
		if (user.isPresent())
		{
			user.get().setUsername(updatedUser.getUsername());
			service.saveUser(user.get());
		}
	}
	
	// I should probably ask for the user's current password to be sure of this. I'd have to encrypt it over traffic though.
	@PutMapping("/changePassword")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void changePassword(@RequestBody User updatedUser, Principal principal){
		Optional<User> user = service.getUserByUsername(principal.getName());
		if (user.isPresent() && updatedUser.getUsername() == user.get().getUsername()) {
			user.get().setPassword(updatedUser.getPassword());
			service.encodeAndChangePassword(user.get());
		}
	}
	
	@PutMapping("/changeEmail")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void changeEmail(@RequestBody User updatedUser, Principal principal){
		Optional<User> user = service.getUserByUsername(principal.getName());
		if (user.isPresent()) // If this user exists in the database.
		{
			user.get().setEmail(updatedUser.getEmail());
			service.saveUser(user.get());
		}
	}
	*
	
	@DeleteMapping("/user/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void deleteUser(@PathVariable Integer id) {
		service.deleteUser(id);
	}
	*/
}
