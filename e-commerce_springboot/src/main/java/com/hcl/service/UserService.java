package com.hcl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcl.entity.Role;
import com.hcl.entity.User;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.UserRepository;

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
	
	public void saveUser(User user) {
		repo.save(user);
	}
	
	public void encodeAndChangePassword(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
	}

	public void deleteUser(Integer id) {
		repo.deleteById(id);
	}

	// Todo: check if username exists. Currently mySQL doesn't create the row, but Hibernate marks an id for it.
	// This results in the id skipping numbers, since it's generator gets confused.
	public void addUser(User user, int roleNum) {
		Role role = new Role();
		//roleNum is 0 if null, so it is incremented by 1. RoleID of 1 corresponds to ROLE_CUSTOMER.
		role.setRoleId(roleNum);
		user.getRoles().add(role);
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		// user.getRoles().forEach(r -> System.out.println(r.getRoleType()));
		SendEmail.sendRegistrationEmail(user.getEmail(), user.getUsername());
	}
}
