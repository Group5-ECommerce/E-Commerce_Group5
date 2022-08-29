package com.hcl.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.Purchase;
import com.hcl.entity.Address;
import com.hcl.entity.Order;
import com.hcl.entity.OrderItem;
import com.hcl.entity.PaymentInfo;
import com.hcl.entity.Product;
import com.hcl.entity.User;
import com.hcl.model.cartItem;
import com.hcl.repo.AddressRepository;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.PaymentRepository;
import com.hcl.service.AddressService;
import com.hcl.service.OrderService;
import com.hcl.service.SendEmail;
import com.hcl.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private AddressService addressService;

	
	@PostMapping("/checkout")
	//@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public Purchase checkout(HttpSession session, Principal principal, @RequestBody Purchase p) {
		List<cartItem> items = (ArrayList<cartItem>) session.getAttribute("items");
		System.out.println(items);
		if (items == null)
			System.out.println("null");
		
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
		User u = userService.getUserByUsername(principal.getName()).get();
		
		Address s = p.getPayment().getShippingAddressId();
		Address b = p.getPayment().getBillingAddressId();
		
		addressService.addAddress(u, s);
		addressService.addAddress(u, b);
		
		order.setOrderStatus("Processing");
		order.setOrderTime(new Timestamp(System.currentTimeMillis()));
		order.setItems(orderItems);
		order.setTotalPrice(totalPrice);
		order.setUser(u);
		order.setShippingAddress(p.getPayment().getShippingAddressId());
		String number = generateTrackingNumber();
		order.setTrackingNumber(number);
		orderService.save(order);
		
		PaymentInfo payment = new PaymentInfo();
		
		payment.setBillingAddressId(p.getPayment().getBillingAddressId());
		payment.setShippingAddressId(p.getPayment().getShippingAddressId());
		payment.setCardHolderFirstName(p.getPayment().getCardHolderFirstName());
		payment.setCardHolderLastName(p.getPayment().getCardHolderLastName());
		payment.setCardNumber(p.getPayment().getCardNumber());
		payment.setOrder(order);
		paymentRepo.save(payment);
				
		// Creates an order based on the list of products and amounts.
		
		
		SendEmail.sendOrderConfirmation(u.getEmail(), u.getUsername(), order);
		items = null;
		session.setAttribute("items", items);
		
		String message = p.getMessage();
		return new Purchase(payment, message);
		
	}
	
	public String generateTrackingNumber()
	{
		return UUID.randomUUID().toString();
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
