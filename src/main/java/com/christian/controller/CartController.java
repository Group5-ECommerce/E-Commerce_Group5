package com.christian.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.christian.entity.Product;
import com.christian.entity.User;
import com.christian.entity.Order;
import com.christian.model.cartItem;
import com.christian.repo.OrderRepository;
import com.christian.repo.ProductRepository;
import com.christian.service.UserService;

@RestController
public class CartController {
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	UserService userService;

	@PostMapping("/cart/{id}/{amt}")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void addItemToCart(@PathVariable Integer id, @PathVariable Integer amt, HttpSession session) {
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null)
			items = new ArrayList<cartItem>();
		items.add(new cartItem(id, amt));
		session.setAttribute("items", items);
	}

	@GetMapping("/cart")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public Object getCart(HttpSession session) {
		System.out.println(session.toString());
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null)
			return null;
		return productRepo.findAllById(items.stream().map(i -> i.itemId).collect(Collectors.toList()));
	}

	@GetMapping("/cartAsIds")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public Object getCartAsIds(HttpSession session) {
		return session.getAttribute("items");
	}

	@PutMapping("/cart/{id}/{amt}")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public String updateItemInCart(@PathVariable Integer id, @PathVariable Integer amt, HttpSession session) {
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null)
			items = new ArrayList<cartItem>();

		int indexOfItem = -1;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getAmt() == id) {
				indexOfItem = i;
				break;
			}
		}

		if (indexOfItem >= 0) {
			items.get(indexOfItem).setAmt(amt);
			session.setAttribute("items", items);
			return "Updated item";
		} else
			return "Failed to update: item of id " + id + " not in cart.";

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
		if (items == null)
			items = new ArrayList<cartItem>();
			
		// Thanks to https://www.geeksforgeeks.org/how-to-solve-concurrentmodificationexception-in-java/ for explaining 
		// why Lists run into a concurrent modification exception, but iterators do not.
		Iterator<cartItem> iter = items.iterator();
		while(iter.hasNext()) {
			if (iter.next().getItemId() == id) iter.remove();
		}
		session.setAttribute("items", items);
	}

	@GetMapping("/checkout")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void checkout(HttpSession session, Principal principal) {
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null)
			return;
		List<Product> products = productRepo.findAllById(items.stream().map(i -> i.itemId).collect(Collectors.toList()));
		
		double totalPrice = 0.00;
		Iterator<Product> iter = products.iterator();
		while(iter.hasNext()) {
			totalPrice += iter.next().getProductPrice();
		}
		Optional<User> user = userService.getUserByUsername(principal.getName());
		
		Order order = new Order();
		order.setOrderStatus("Processing");
		order.setOrderTime(new Timestamp(System.currentTimeMillis()));
		order.setUserId(user.get().getUserId());
		order.setTotalPrice(totalPrice);
		order.setShippingAddressId(0);
		orderRepo.save(order);
		
		items = null;
		session.setAttribute("items", items);
	}
}
