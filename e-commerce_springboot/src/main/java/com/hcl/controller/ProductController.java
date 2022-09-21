package com.hcl.controller;

import java.security.Principal;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "https://white-stone-04a29cc10.1.azurestaticapps.net")
@RestController
@Api(tags = "Products")
public class ProductController {
	@Autowired
	private ProductRepository repo;

	@PostMapping("/product")
	@ApiOperation(value = "Add Product")
	@PreAuthorize("hasAuthority('Admin')")
	public void addProduct(@RequestBody Product product) {
		repo.save(product);
	}

	@GetMapping("/product")
	@ApiOperation(value = "Show All Products")
	public List<Product> listAllProduct() {
		return repo.findAll();
	}

	@GetMapping("/product/{id}")
	@ApiOperation(value = "Get Product With Id")
	public Optional<Product> getProductId(@PathVariable Integer id) {
		Optional<Product> product = repo.findById(id);
		return product;
	}

	@PutMapping("/product")
	@ApiOperation(value = "Update specific product")
	@PreAuthorize("hasAuthority('Admin')")
	public void updateProduct(@RequestBody Product newProduct) {
		repo.save(newProduct);
	}

	@DeleteMapping("/product/{id}")
	@ApiOperation(value = "Delete Product With Id")
	@PreAuthorize("hasAuthority('Admin')")
	public void deleteProduct(@PathVariable Integer id) {
		repo.deleteById(id);
	}
}
