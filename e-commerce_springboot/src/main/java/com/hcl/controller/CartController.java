package com.hcl.controller;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.entity.Order;
import com.hcl.entity.OrderItem;
import com.hcl.entity.Product;
import com.hcl.entity.User;
import com.hcl.model.cartItem;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.ProductRepository;
import com.hcl.service.SendEmail;
import com.hcl.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CartController {
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	UserService userService;

	@PostMapping("/cart/{id}/{amt}")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public String addItemToCart(@PathVariable Integer id, @PathVariable Integer amt, HttpSession session) {
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null)
			items = new ArrayList<cartItem>();
		// See if the productId they are trying to add corresponds to a real product.
		Optional<Product> product = productRepo.findById(id);
		if(product.isPresent()) {
			items.add(new cartItem(product.get(), amt));
			session.setAttribute("items", items);
			return "";
		}
		else return "A product with id " + id + " does not exist.";
	}

	@GetMapping("/cart")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public Object getCart(HttpSession session) {
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null)
			return null;
		// The items attribute stores a list of productIds and amounts. This returns all of the products corresponding to the product ids.
		return items;
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
		// Find the index of the item in the cart.
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getProduct().getProductId() == id) {
				indexOfItem = i;
				break;
			}
		}

		// If the cart contains an item with the requested product id, then update its amount.
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
			if (iter.next().getProduct().getProductId() == id) iter.remove();
		}
		session.setAttribute("items", items);
	}
}
