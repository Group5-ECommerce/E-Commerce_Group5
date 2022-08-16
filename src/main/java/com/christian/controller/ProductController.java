package com.christian.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.christian.model.Product;
import com.christian.repo.ProductRepository;

@RestController
public class ProductController {
	@Autowired
	private ProductRepository repo;
	
	@PostMapping("/product")
	public void addProduct(@RequestBody Product product) {
		repo.save(product);
	}

	@GetMapping("/product")
    // @PreAuthorize("hasAuthority('ADMIN')")
	public List<Product> listAllProduct() {
		return repo.findAll();
	}
	
	@GetMapping("/product/{id}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Optional<Product> getProductId(@PathVariable Integer id) {
		Optional<Product> product = repo.findById(id);
		return product;
	}
	
	@PutMapping("/product")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public void updateProduct(@RequestBody Product newProduct){
		repo.save(newProduct);
	}
	
	@DeleteMapping("/product/{id}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public void deleteProduct(@PathVariable Integer id) {
		repo.deleteById(id);
	}
}
