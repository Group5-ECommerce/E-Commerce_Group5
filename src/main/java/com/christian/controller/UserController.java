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

	@GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<User> listAllUser() {
		return service.getAllUsers();

	}

	@GetMapping("/users/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Optional<User> getUserId(@PathVariable Integer id) {
		Optional<User> user = service.getUserById(id);
		return user;
	}
	
	@GetMapping("/loginUser")
	public String userLogin() {
		return "<p>User login page</p>\r\n"
				+ "<form name=\"f\" action=\"user_login\" method=\"POST\">\r\n"
				+ "    <table>\r\n"
				+ "        <tr>\r\n"
				+ "            <td>User:</td>\r\n"
				+ "            <td><input type=\"text\" name=\"username\" value=\"\"></td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "            <td>Password:</td>\r\n"
				+ "            <td><input type=\"password\" name=\"password\" /></td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "            <td><input name=\"submit\" type=\"submit\" value=\"submit\" /></td>\r\n"
				+ "        </tr>\r\n"
				+ "    </table>\r\n"
				+ "</form>";
	}
	
	@PutMapping("/users")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void updateUser(@RequestBody User newuser){
		service.updateUser(newuser);
	}

	@PostMapping("/users")
	public void addUser(@RequestBody User user) {
		service.addUser(user);
	}
}
