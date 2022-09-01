package com.hcl.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.entity.Order;
import com.hcl.entity.OrderItem;
import com.hcl.entity.Product;
import com.hcl.entity.User;
import com.hcl.model.cartItem;
import com.hcl.repo.OrderRepository;
import com.hcl.service.OrderService;
import com.hcl.service.SendEmail;
import com.hcl.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags= "Products")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@PutMapping("/checkout")
    @PreAuthorize("hasAuthority('Customer')")
	@ApiOperation(value = "Checkout for Order")
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
    @PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "Gets All Orders")
	public List<Order> getAllOrders(){
		return orderService.findAll();
	}
	
	@GetMapping("/myOrders")
    @PreAuthorize("hasAuthority('Customer')")
	@ApiOperation(value = "Gets all Orders by Username")
	public List<Order> getMyOrders(Principal principal){
		return orderService.findByUsername(principal.getName());
	}
  
	@GetMapping("/trackOrder/{trackingNumber}")
    @PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "Find Order by Tracking Number")
	public String trackOrder(@PathVariable String trackingNumber) {
		Optional<Order> order = orderService.findByTrackingNumber(trackingNumber);
		if (order.isPresent()) {
			return order.get().getOrderStatus();
		}
		else return "An order of id " + trackingNumber + " does not exist.";
	}
}
