package com.hcl.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.entity.Order;
import com.hcl.entity.User;

 
public interface OrderRepository extends JpaRepository<Order, Integer>{
	List<Order> findAllByUser(User user);

	Optional<Order> findByTrackingNumber(String trackingNumber);
}


