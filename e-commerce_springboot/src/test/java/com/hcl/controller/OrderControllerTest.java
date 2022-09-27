package com.hcl.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.entity.Order;
import com.hcl.entity.OrderItem;
import com.hcl.repo.AddressRepository;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.PaymentRepository;
import com.hcl.repo.ProductRepository;
import com.hcl.service.AddressService;
import com.hcl.service.OrderService;

//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc
@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

	@MockBean
	private OrderService orderService;

	@MockBean
	private AddressRepository addressRepo;

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

//	@InjectMocks
//	OrderService service;
//
//	@Mock
//	OrderRepository repository;
//	

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

//		Principal mockPrincipal = Mockito.mock(Principal.class);
//		Mockito.when(mockPrincipal.getName()).thenReturn("dmqkirezbllxgnitjm@bvhrk.com"); // customer
//		when(orderService.findAll()).thenReturn(list);

//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order").principal(mockPrincipal)
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andExpect(jsonPath("$.length()", is(3))).andReturn();
//
//		MockHttpServletResponse response = result.getResponse();
//		int status = response.getStatus();
//		assertEquals(status, 200);

//		mockMvc.perform(get("/order").with(user("customer").password("pass").roles("Customer"))
//				.contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()", is(3)));

		when(orderService.findAll()).thenReturn(list);
		mockMvc.perform(get("/order").with(jwt().authorities(customerAuthority)
//				.jwt(jwt -> jwt.header("kid", "one").header("alg", "RS256")
//				.claim("iss", "https://dev-14334923.okta.com").claim("sub", "1").claim("name", "christian customer")
//				.claim("email", "email@email.com").claim("group", "Customer"))
		)

				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is(200))
				.andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void getAllOrder_Other() throws Exception {
		List<Order> list = new ArrayList();
		list.add(new Order());
		list.add(new Order());
		list.add(new Order());

		Collection<GrantedAuthority> authorities = new ArrayList();
		authorities.add(adminAuthority);
		authorities.add(new SimpleGrantedAuthority("Everyone"));

		when(orderService.findAll()).thenReturn(list);

		mockMvc.perform(get("/order").with(jwt().authorities(authorities)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isForbidden());
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
	void getMyOrders_Customer() {

	}

	@Test
	void creatingPayment_Customer() {

	}

	@Test
	void checkout_Customer() {

	}
}
