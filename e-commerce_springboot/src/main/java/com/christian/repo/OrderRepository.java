package com.christian.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.christian.entity.Order;
import com.christian.entity.User;

 
public interface OrderRepository extends JpaRepository<Order, Integer>{
	List<Order> findAllByUser(User user);
}


