package com.christian.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.christian.model.User;
import com.christian.repo.UserRepository;
import com.christian.SendEmail;

@Service
public class UserService {

	@Autowired
	UserRepository repo; // HAS A

	public List<User> getAllUsers() {
		return repo.findAll();
	}

	public Optional<User> getUserById(int id) {
		return repo.findById(id);
	}

	public void updateUser(User updatedUser) {
		try {
			// See if the employee exists
			Optional<User> opt = repo.findById(updatedUser.getUserId());
			User emp = opt.get();

			// Set the existing employee's properties to those of the updated
			emp.setUsername(updatedUser.getUsername());
			emp.setPassword(updatedUser.getPassword());
			emp.setEmail(updatedUser.getEmail());
			repo.save(emp);
		} catch (NoSuchElementException | IllegalArgumentException e) {

		}
	}

	public void deleteUser(Integer id) {
		repo.deleteById(id);
	}

	public void addUser(User user) {
		try {
			repo.save(user);
			SendEmail.sendMessage(user.getEmail());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}
