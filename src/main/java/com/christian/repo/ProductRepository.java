package com.christian.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.christian.model.Product;

 
public interface ProductRepository extends JpaRepository<Product, Integer>{
}

