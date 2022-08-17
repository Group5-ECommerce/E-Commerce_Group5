package com.christian.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
public class CartController {
	@Autowired
	private ProductRepository repo;
	
	@PostMapping("/cart/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void addItemToCart(@PathVariable Integer id, HttpSession session) {
		List<Integer> items = (List<Integer>) session.getAttribute("items");
		if (items == null) items = new ArrayList<Integer>();
		items.add(id);
		System.out.println("Cart items: " + items);
		session.setAttribute("items", items);
	}
	
	@GetMapping("/cart")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public Object getCart(HttpSession session) {
		List<Integer> items = (List<Integer>) session.getAttribute("items");
		return repo.findAllById(items);
	}
	
	//Todo: make it an array of two variables. Item and quantity.
	@PutMapping("/cart/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void updateItemInCart(@PathVariable Integer id, HttpSession session) {
		List<Integer> items = (List<Integer>) session.getAttribute("items");
		if (items == null) items = new ArrayList<Integer>();
		items.add(id);
		System.out.println("Cart items: " + items);
		session.setAttribute("items", items);
	}
	
	@DeleteMapping("/cart/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void removeItemFromCart(@PathVariable Integer id, HttpSession session) {
		List<Integer> items = (List<Integer>) session.getAttribute("items");
		if (items == null) items = new ArrayList<Integer>();
		items.remove(id);
		System.out.println("Cart items: " + items);
		session.setAttribute("items", id);
	}
}
