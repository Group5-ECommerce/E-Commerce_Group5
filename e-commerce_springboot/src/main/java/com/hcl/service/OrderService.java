package com.hcl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.entity.Order;
//import com.hcl.entity.User;
import com.hcl.repo.OrderRepository;
//import com.hcl.service.UserService;

@Service
public class OrderService{

	@Autowired
	OrderRepository orderRepo;

	public List<Order> findAll() {
		return orderRepo.findAll();
	}
	public void save(Order order) {
		orderRepo.save(order);
	}
	public Optional<Order> findById(Integer id) {
		return orderRepo.findById(id);
	}

	public Optional<Order> findByTrackingNumber(String trackingNumber) {
		return orderRepo.findByTrackingNumber(trackingNumber);
	}
	public List<Order> findByOktaId(String oktaId) 
	{
		return orderRepo.findByOktaId(oktaId);
	}

}
