package com.hcl.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.entity.Product;
import com.hcl.repo.ProductRatingRepository;
import com.hcl.repo.ProductRepository;

@WebMvcTest(controllers = ProductController.class)
//@TestInstance(Lifecycle.PER_CLASS)
public class WebMvcProductControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ProductRepository repository;
	
	@MockBean
	ProductRatingRepository ratingRepo;

	@Autowired
	ObjectMapper mapper;

	private GrantedAuthority adminAuthority;

	@BeforeEach
	public void setup() {
		adminAuthority = new SimpleGrantedAuthority("Admin");
//		mapper.registerModule(new Jdk8Module()); // json optional

	}

	@AfterEach
	public void fin() {
		repository.deleteAll();
	}

	@Test
	void listAllProduct() throws Exception {
		List<Product> list = new ArrayList();
		list.add(new Product());
		list.add(new Product());
		list.add(new Product());

		when(repository.findAll()).thenReturn(list);
		mockMvc.perform(get("/product").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void getProductId() throws Exception {

		Product product = new Product();
		product.setProductId(1);
		when(repository.findById(1)).thenReturn(Optional.of(product));
		mockMvc.perform(get("/product/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.productId", is(1)));
	}

	@Test
	void addProduct_Admin() throws JsonProcessingException, Exception {
		Product product = new Product();
		when(repository.save(product)).thenReturn(product);
		mockMvc.perform(post("/product").with(jwt().authorities(adminAuthority)).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(product))).andDo(print()).equals(product);

	}

	@Test
	void updateProduct_Admin() throws JsonProcessingException, Exception {
		Product product = new Product();
		when(repository.save(product)).thenReturn(product);
		mockMvc.perform(put("/product").with(jwt().authorities(adminAuthority)).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(product))).andDo(print()).equals(product);

	}

	@Test
	void deleteProduct_Admin() throws Exception {

		mockMvc.perform(delete("/product/{id}", 123).with(jwt().authorities(adminAuthority))).andDo(print())
				.andExpect(status().isOk());

	}

}
