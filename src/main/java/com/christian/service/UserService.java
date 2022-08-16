package com.christian.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.christian.controller.SendEmail;
import com.christian.model.Role;
import com.christian.model.User;
import com.christian.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	public List<User> getAllUsers() {
		return repo.findAll();
	}

	public Optional<User> getUserById(int id) {
		return repo.findById(id);
	}
	
	public Optional<User> getUserByUsername(String username) {
		return repo.findByUsername(username);
	}

	public void updateUser(User updatedUser) {
		repo.save(updatedUser);
	}

	public void deleteUser(Integer id) {
		repo.deleteById(id);
	}

	public void addUser(User user, int roleNum) {
		Role role = new Role();
		if (roleNum == 1) {
			role.setRoleType("ADMIN");
		}
		else role.setRoleType("CUSTOMER");
		user.getRoles().add(role);
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		SendEmail.sendMessage(user.getEmail());
	}
}
