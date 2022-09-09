package com.hcl.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hcl.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
}


