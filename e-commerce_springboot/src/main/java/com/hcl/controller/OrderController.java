package com.hcl.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.Payment;
import com.hcl.config.MQConfig;
import com.hcl.dto.Purchase;
import com.hcl.entity.Address;
import com.hcl.entity.Order;
import com.hcl.entity.OrderItem;
import com.hcl.entity.PaymentInfo;
import com.hcl.entity.Product;
import com.hcl.model.Message;
//import com.hcl.entity.User;
import com.hcl.model.cartItem;
import com.hcl.repo.AddressRepository;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.PaymentRepository;
import com.hcl.repo.ProductRepository;
//import com.hcl.repo.UserRepository;
import com.hcl.service.AddressService;
import com.hcl.service.OrderService;
import com.hcl.service.SendEmail;
//import com.hcl.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Order")
public class OrderController {
	@Autowired
	private OrderService orderService;

//	@Autowired
//	UserService userService;

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private AddressService addressService;

//	@Autowired
//	private UserRepository userRepo;

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ProductRepository productRepository;

	@Value("${stripe.key.secret}")
	String secretKey;

	@Autowired
	private RabbitTemplate template;

	@PostMapping("/checkout/{email}/{name}")
	@PreAuthorize("hasAuthority('Customer') and !hasAuthority('Admin')")
	@ApiOperation(value = "Checkout for Order")
	public Purchase checkout(@RequestBody Purchase p, @PathVariable String email, @PathVariable String name,
			Principal principal) {
		String oktaId = principal.getName();
		List<cartItem> items = p.getItems();
		if (items == null)
			System.out.println("null");

		// Creates a list of products from the cart items.
		// Principal principal
		List<OrderItem> orderItems = new ArrayList<OrderItem>();

		Order order = new Order();

		double totalPrice = 0.00;
		// Loops through the items list to create orderItem entries from it,
		// decrease product stock, and calculate total price.
		for (int i = 0; i < items.size(); i++) {
			Product product = items.get(i).getProduct();
			int amt = items.get(i).getAmt();
			// Adds an OrderItem entry connected to this order, which holds a product and
			// amount.

			orderItems.add(new OrderItem(order, product, amt));

			totalPrice += product.getProductPrice() * amt;

			// save stock changes after checkout
			product.setProductStock(product.getProductStock() - amt);
			if (product.getProductStock() < 5) {
				Message message = new Message(UUID.randomUUID().toString(), product.getProductName() + " is out of stock.", new Date());
				template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, message);
				}
			productRepository.save(product);
		}

		// User u = userRepo.findByOktaId(oktaId).get();

		Address s = p.getPayment().getShippingAddressId();
		Address b = p.getPayment().getBillingAddressId();

		addressService.addAddress(oktaId, s);
		addressService.addAddress(oktaId, b);

		order.setOrderStatus("Processing");
		order.setOrderTime(new Timestamp(System.currentTimeMillis()));
		order.setItems(orderItems);
		order.setTotalPrice(totalPrice);
		order.setOktaId(oktaId);
		order.setShippingAddress(p.getPayment().getShippingAddressId());
		String number = generateTrackingNumber();
		order.setTrackingNumber(number);
		orderRepo.save(order);

		PaymentInfo payment = new PaymentInfo();

		payment.setBillingAddressId(p.getPayment().getBillingAddressId());
		payment.setShippingAddressId(p.getPayment().getShippingAddressId());
		payment.setOrder(order);
		paymentRepo.save(payment);

		// Creates an order based on the list of products and amounts.

		SendEmail.sendOrderConfirmation(email, name, order);

		String message = p.getMessage();
		return new Purchase(payment, items, message);

	}

	public String generateTrackingNumber() {
		return UUID.randomUUID().toString();
	}

	public PaymentIntent createPayment(Payment pmt) throws StripeException {
		List<String> paymentMethodTypes = new ArrayList<>();
		paymentMethodTypes.add("card");
		Map<String, Object> map = new HashMap<>();
		map.put("amount", pmt.getAmount());
		map.put("currency", pmt.getCurrency());
		map.put("payment_method_types", paymentMethodTypes);
		map.put("description", "E-Commerce Purchase");

		return PaymentIntent.create(map);

	}

	@PostMapping("/payment-intent")
	public ResponseEntity<String> creatingPayment(@RequestBody Payment pmt) throws StripeException {
		Stripe.apiKey = secretKey;
		PaymentIntent p = this.createPayment(pmt);

		String pmtStr = p.toJson();

		return new ResponseEntity<>(pmtStr, HttpStatus.OK);
	}

	@GetMapping("/order")
	@ApiOperation(value = "Gets All Orders")
	@PreAuthorize("hasAuthority('Customer')")
	public List<Order> getAllOrders() {
		return orderService.findAll();
	}

	@GetMapping("/myOrders")
	@PreAuthorize("hasAuthority('Customer')")
	@ApiOperation(value = "Gets all Orders by Username")
	public List<Order> getMyOrders(Principal principal) {
		return orderService.findByOktaId(principal.getName());

	}

	@GetMapping("/orderItems/{trackingNumber}")
	@ApiOperation(value = "Find Order by Tracking Number")
	@PreAuthorize("hasAuthority('Customer')")
	public List<OrderItem> getOrderItemsByTrackingNumber(@PathVariable String trackingNumber) {
		System.out.println(trackingNumber);
		Optional<Order> order = orderService.findByTrackingNumber(trackingNumber);
		if (order.isPresent()) {
			return order.get().getItems();
		}
		return null;
	}

	@GetMapping("/order/{trackingNumber}")
	@ApiOperation(value = "Find Order by Tracking Number")
	@PreAuthorize("hasAuthority('Customer') or hasAuthority('Admin')")
	public Order trackOrder(@PathVariable String trackingNumber) {
		System.out.println(trackingNumber);
		Optional<Order> order = orderService.findByTrackingNumber(trackingNumber);
		if (order.isPresent()) {
			return order.get();
		}
		return null;
	}

	@PutMapping("/order")
	@PreAuthorize("hasAuthority('Admin')")
	public Order changeOrderStatus(@RequestBody Order order) {
		Optional<Order> persistedOrder = orderService.findById(order.getOrderId());
		if (persistedOrder.isPresent()) {
			Order persisted = persistedOrder.get();
			persisted.setOrderStatus(order.getOrderStatus());
			return orderRepo.save(persisted);
		}
		return null;
	}
}
