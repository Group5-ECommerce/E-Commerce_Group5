package com.christian.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private ProductRepository productRepo;
	
	@PostMapping("/cart/{id}/{amt}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void addItemToCart(@PathVariable Integer id, @PathVariable Integer amt, HttpSession session) {
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null) items = new ArrayList<cartItem>();
		items.add(new cartItem(id, amt));
		session.setAttribute("items", items);
	}
	
	@GetMapping("/cart")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public Object getCart(HttpSession session) {
		System.out.println(session.toString());
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null) return null;
		return productRepo.findAllById(items.stream().map(i->i.itemId).collect(Collectors.toList()));
	}
	
	@GetMapping("/cartAsIds")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public Object getCartAsIds(HttpSession session) {
		return session.getAttribute("items");
	}
	
	//Todo: make it an array of two variables. Item and quantity.
	@PutMapping("/cart/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void updateItemInCart(@PathVariable Integer id, @PathVariable Integer amt, HttpSession session) {
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null) items = new ArrayList<cartItem>();
		items.add(new cartItem(id, amt));
		session.setAttribute("items", items);
	}
	
	@DeleteMapping("/cart")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void emptyCart(HttpSession session) {
		session.setAttribute("items", null);
	}
	
	
	@DeleteMapping("/cart/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void removeItemFromCart(@PathVariable Integer id, HttpSession session) {
		List<cartItem> items = (List<cartItem>) session.getAttribute("items");
		if (items == null) items = new ArrayList<cartItem>();
		items.remove(id);
		session.setAttribute("items", id);
	}
	
	final class cartItem
	{
		public int itemId, amt;
		public cartItem(int id, int amt) {
			this.itemId = id;
			this.amt = amt;
		}
	}
}
