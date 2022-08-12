package com.christian.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.christian.controller.SendEmail;
import com.christian.model.User;
import com.christian.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;

	public List<User> getAllUsers() {
		return repo.findAll();
	}

	public Optional<User> getUserById(int id) {
		return repo.findById(id);
	}

	public void updateUser(User updatedUser) {
		repo.save(updatedUser);
	}

	public void deleteUser(Integer id) {
		repo.deleteById(id);
	}

	public void addUser(User user) {
		repo.save(user);
		SendEmail.sendMessage(user.getEmail());
	}
}
