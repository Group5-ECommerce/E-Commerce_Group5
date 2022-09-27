package com.hcl.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hcl.entity.Order;
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

//	@InjectMocks
//	OrderService service;
//
//	@Mock
//	OrderRepository repository;
//	

//	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	public void init(WebApplicationContext context) {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
//	@WithMockUser(roles = "Customer")
	void getAllOrdersCustomer() throws Exception {
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
		mockMvc.perform(get("/order").with(jwt().jwt(jwt -> jwt.header("kid", "one").header("alg", "RS256")
				.claim("iss", "https://dev-14334923.okta.com").claim("sub", "1").claim("name", "christian customer")
				.claim("email", "email@email.com").claim("group", "Customer")))

				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is(200))
				.andExpect(jsonPath("$.length()", is(3)));
	}

}
