package com.christian.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.christian.entity.Order;
import com.christian.entity.OrderItem;
import com.christian.entity.Product;
import com.christian.entity.User;
import com.christian.model.cartItem;
import com.christian.repo.OrderRepository;
import com.christian.service.OrderService;
import com.christian.service.SendEmail;
import com.christian.service.UserService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@PutMapping("/checkout")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public void checkout(HttpSession session, Principal principal) {
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		if (items == null)
			return;
		
		// Creates a list of products from the cart items.
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		Order order = new Order();

		double totalPrice = 0.00;
		// Loops through the items list to create orderItem entries from it, 
		// decrease product stock, and calculate total price.
		for (int i = 0; i < items.size(); i++) {
			Product product = items.get(i).getProduct();
			int amt = items.get(i).getAmt();
			// Adds an OrderItem entry connected to this order, which holds a product and amount.
			orderItems.add(new OrderItem(order, product, amt));
			// Decrease the product's stock since it has just been ordered.
			product.setProductStock(product.getProductStock() - amt);
			totalPrice += product.getProductPrice() * amt;
		}
		Optional<User> user = userService.getUserByUsername(principal.getName());
		
		// Creates an order based on the list of products and amounts.
		order.setOrderStatus("Processing");
		order.setOrderTime(new Timestamp(System.currentTimeMillis()));
		order.setUser(user.get());
		order.setItems(orderItems);
		order.setTotalPrice(totalPrice);
		order.setShippingAddressId(0);
		orderService.save(order);
		
		SendEmail.sendOrderConfirmation(user.get().getEmail(), user.get().getUsername(), order);
		items = null;
		session.setAttribute("items", items);
	}
	
	@GetMapping("/order")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Order> getAllOrders(){
		return orderService.findAll();
	}
	
	@GetMapping("/myOrders")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public List<Order> getMyOrders(Principal principal){
		return orderService.findByUsername(principal.getName());
	}
	
	@GetMapping("/trackOrder/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String trackOrder(@PathVariable Integer id) {
		Optional<Order> order = orderService.findById(id);
		if (order.isPresent()) {
			return order.get().getOrderStatus();
		}
		else return "An order of id " + id + " does not exist.";
	}
}
