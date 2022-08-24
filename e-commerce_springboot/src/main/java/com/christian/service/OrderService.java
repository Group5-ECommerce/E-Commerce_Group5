package com.christian.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.christian.entity.Order;
import com.christian.entity.User;
import com.christian.repo.OrderRepository;
import com.christian.service.UserService;

@Service
public class OrderService{
	@Autowired
	UserService userService;
	
	@Autowired
	OrderRepository orderRepo;
	public List<Order> findByUsername(String username){
		Optional<User> user = userService.getUserByUsername(username);
		
		return orderRepo.findAllByUser(user.get());
	}
	public List<Order> findAll() {
		return orderRepo.findAll();
	}
	public void save(Order order) {
		orderRepo.save(order);
	}
	public Optional<Order> findById(Integer id) {
		return orderRepo.findById(id);
	}
}
