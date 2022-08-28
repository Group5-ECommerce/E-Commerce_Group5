package com.hcl.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.entity.Product;
import com.hcl.repo.ProductRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
	@Autowired
	private ProductRepository repo;

	@PostMapping("/product")
<<<<<<< HEAD
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
=======
    @PreAuthorize("hasAuthority('Admin')")
>>>>>>> 45898151d97d1eda3a79bea0154b74a6a50ea620
	public void addProduct(@RequestBody Product product) {
		repo.save(product);
	}

	@GetMapping("/product")
<<<<<<< HEAD
//	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_CUSTOMER')")
=======
    //@PreAuthorize("hasAuthority('Customer')")
>>>>>>> 45898151d97d1eda3a79bea0154b74a6a50ea620
	public List<Product> listAllProduct() {
		return repo.findAll();
	}

	@GetMapping("/product/{id}")
    @PreAuthorize("hasAuthority('Customer')")
	public Optional<Product> getProductId(@PathVariable Integer id) {
		Optional<Product> product = repo.findById(id);
		return product;
	}

	@PutMapping("/product")
<<<<<<< HEAD
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void updateProduct(@RequestBody Product newProduct) {
=======
    @PreAuthorize("hasAuthority('Admin')")
	public void updateProduct(@RequestBody Product newProduct){
>>>>>>> 45898151d97d1eda3a79bea0154b74a6a50ea620
		repo.save(newProduct);
	}

	@DeleteMapping("/product/{id}")
    @PreAuthorize("hasAuthority('Admin')")
	public void deleteProduct(@PathVariable Integer id) {
		repo.deleteById(id);
	}
}
