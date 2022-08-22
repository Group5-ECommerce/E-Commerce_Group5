package com.christian.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.christian.entity.Order;

 
public interface OrderRepository extends JpaRepository<Order, Integer>{
}


