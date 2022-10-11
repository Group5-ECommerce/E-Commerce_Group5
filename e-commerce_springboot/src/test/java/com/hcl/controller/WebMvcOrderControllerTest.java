package com.hcl.controller;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.dto.Payment;
import com.hcl.dto.Purchase;
import com.hcl.entity.Order;
import com.hcl.entity.OrderAddress;
import com.hcl.entity.OrderItem;
import com.hcl.entity.PaymentInfo;
import com.hcl.entity.Product;
import com.hcl.model.cartItem;
import com.hcl.repo.OrderAddressRepository;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.PaymentRepository;
import com.hcl.repo.ProductRepository;
import com.hcl.service.AddressService;
import com.hcl.service.OrderService;
import com.hcl.service.SendEmail;

//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc

//spring sec auto-configured
@WebMvcTest(controllers = OrderController.class)
//spring sec non-auto config
//@SpringBootTest
//@AutoConfigureMockMvc
public class WebMvcOrderControllerTest {

	
	@MockBean
	private OrderService orderService;

	@MockBean
	private OrderAddressRepository addressRepo;

	@MockBean
	private PaymentRepository paymentRepo;

	@MockBean
	private AddressService addressService;

	@MockBean
	private OrderRepository orderRepo;

	@MockBean
	private ProductRepository productRepository;

	private GrantedAuthority customerAuthority;
	private GrantedAuthority adminAuthority;

	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private RabbitTemplate template;
	
	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	public void init(WebApplicationContext context) {
//		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		customerAuthority = new SimpleGrantedAuthority("Customer");
		adminAuthority = new SimpleGrantedAuthority("Admin");
	}

	@Test
//	@WithMockUser(roles = "Customer")
	void getAllOrders_Customer() throws Exception {
		List<Order> list = new ArrayList();
		list.add(new Order());
		list.add(new Order());
		list.add(new Order());

		when(orderService.findAll()).thenReturn(list);
		mockMvc.perform(get("/order").with(jwt().authorities(customerAuthority)
//				.jwt(jwt -> jwt.header("kid", "one").header("alg", "RS256")
//				.claim("iss", "https://dev-14334923.okta.com").claim("sub", "1").claim("name", "christian customer")
//				.claim("email", "email@email.com").claim("group", "Customer"))
		)

				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isForbidden());
				// .andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void getAllOrder_Admin() throws Exception {
		List<Order> list = new ArrayList();
		list.add(new Order());
		list.add(new Order());
		list.add(new Order());

		Collection<GrantedAuthority> authorities = new ArrayList();
		authorities.add(adminAuthority);
		authorities.add(new SimpleGrantedAuthority("Everyone"));

		when(orderService.findAll()).thenReturn(list);

		mockMvc.perform(get("/order").with(jwt().authorities(authorities)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(3)));

	}

	@Test
	void getOrderItemsByTrackingNumber_Customer() throws Exception {
		Order order = new Order();
		order.setTrackingNumber(UUID.randomUUID().toString());
		List<OrderItem> orderItems = new ArrayList();
		orderItems.add(new OrderItem());
		orderItems.add(new OrderItem());
		orderItems.add(new OrderItem());
		order.setItems(orderItems);

		when(orderService.findByTrackingNumber(order.getTrackingNumber())).thenReturn(Optional.of(order));
		mockMvc.perform(get("/orderItems/{number}", order.getTrackingNumber()).content(MediaType.APPLICATION_JSON_VALUE)
				.with(jwt().authorities(customerAuthority))).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void changeOrderStatus_Admin() throws Exception {
		Order requestBody = new Order(), persisted = new Order();
		requestBody.setOrderId(1);
		requestBody.setOrderStatus("Shipped");
		persisted.setOrderId(1);
		when(orderService.findById(requestBody.getOrderId())).thenReturn(Optional.of(persisted));
		when(orderRepo.save(persisted)).thenReturn(requestBody);

		mockMvc.perform(put("/order").content(mapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON).with(jwt().authorities(adminAuthority))).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.orderId").value(1))
				.andExpect(jsonPath("$.orderStatus").value("Shipped"));	
	}

	@Test
	@WithMockUser(username = "tuco", authorities = "Customer")
	void getMyOrders_Customer() throws Exception {

		// security context created and user entry provided thanks to mock user
		User userdetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userdetail.getUsername();

		Order order = new Order();
		order.setOktaId(username);
		Order order1 = new Order();
		order1.setOktaId("hector");
		Order order2 = new Order();
		order2.setOktaId(username);

		List<Order> list = new ArrayList();
		list.add(order);
		list.add(order2);

		orderService.save(order);
		orderService.save(order1);
		orderService.save(order2);

		when(orderService.findByOktaId(username)).thenReturn(list);

		mockMvc.perform(get("/myOrders").content(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$[*].oktaId").value(everyItem(is(username))));

		orderService.deleteAll();
	}
	
	@Test
	void trackOrder_Admin() throws Exception {
		Order o = new Order();
		o.setOrderId(1);
		o.setOrderStatus("Shipped");
		Optional<Order> oOp = Optional.of(o);
		
		when(orderService.findByTrackingNumber(o.getTrackingNumber())).thenReturn(oOp);
		when(orderRepo.save(o)).thenReturn(o);

		mockMvc.perform(get("/order/{trackingNumber}", o.getTrackingNumber())
				.contentType(MediaType.APPLICATION_JSON).with(jwt().authorities(adminAuthority))).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void creatingPayment_Customer() throws JsonProcessingException, Exception {
		Payment payment = new Payment();
		payment.setAmount(100);
		payment.setCurrency("eur");
		mockMvc.perform(post("/payment-intent").with(jwt().authorities(customerAuthority))
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(payment))).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$").exists());
	}

	@Test
	@WithMockUser(username = "tuco", authorities = "Customer")
	void checkout_Customer() throws JsonProcessingException, Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String email = username + "@" + username + ".com";
		
		MockedStatic<SendEmail> mockEmail = Mockito.mockStatic(SendEmail.class);
				
		List<cartItem> items = new ArrayList();
		Product p1 = new Product();
		p1.setProductId(1);
		
		Product p2 = new Product();
		p2.setProductId(2);
		
		Product p3 = new Product();
		p3.setProductId(3);
		
		items.add(new cartItem(p1, 1));
		items.add(new cartItem(p2, 2));
		items.add(new cartItem(p3, 3));

		PaymentInfo p = new PaymentInfo();
		when(productRepository.findById(1)).thenReturn(Optional.of(p1));
		when(productRepository.findById(2)).thenReturn(Optional.of(p2));
		when(productRepository.findById(3)).thenReturn(Optional.of(p3));

		
		//Tests the branch where the addresses are not found in the repo, but are the same.
		OrderAddress s = new OrderAddress();
		s.setCity("Horsham");
		s.setCountry("US");
		s.setAddressId(1);
		
		p.setBillingAddressId(s);
		p.setShippingAddressId(s);
		Purchase feed = new Purchase(p, items, "checkout successful");
		
		mockMvc.perform(post("/checkout/{email}/{name}", email, username).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(feed))).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").exists());
		
		// Tests the branch where the addresses are not found in the repo, and are different.
		OrderAddress s2 = new OrderAddress();
		s2.setCity("Zihuatanejo");
		s2.setAddressId(2);
		s2.setCountry("Mexico");
		p.setShippingAddressId(s2);
		
		feed.setPayment(p);
		
		mockMvc.perform(post("/checkout/{email}/{name}", email, username).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(feed))).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").exists());
		
		//Tests the branch where the addresses are found in the repo, and are different.
		List<OrderAddress> addresses = new ArrayList<OrderAddress>();
		addresses.add(s);
		addresses.add(s2);
		
		when(addressRepo.findAll()).thenReturn(addresses);
		mockMvc.perform(post("/checkout/{email}/{name}", email, username).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(feed))).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").exists());
		
		//Tests the branch where the addresses are found in the repo AND the same.
		p.setShippingAddressId(s);
		feed.setPayment(p);
		mockMvc.perform(post("/checkout/{email}/{name}", email, username).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(feed))).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").exists());
	}
}
