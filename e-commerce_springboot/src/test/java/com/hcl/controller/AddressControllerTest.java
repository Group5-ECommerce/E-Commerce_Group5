package com.hcl.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.entity.Address;
import com.hcl.repo.AddressRepository;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.PaymentRepository;
import com.hcl.service.AddressService;

@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	private AddressService addressService;

	@MockBean
	private AddressRepository addressRepo;

	@MockBean
	OrderRepository orderRepo;

	@MockBean
	private PaymentRepository paymentRepo;

	private GrantedAuthority customerAuthority, adminAuthority;

	@Autowired
	ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		customerAuthority = new SimpleGrantedAuthority("Customer");
		adminAuthority = new SimpleGrantedAuthority("Admin");
	}

	@AfterEach
	public void fin() {
		addressRepo.deleteAll();
	}

	@Test
	void addAddress() throws JsonProcessingException, Exception {
		Address address = new Address();
		when(addressRepo.save(address)).thenReturn(address);

		mockMvc.perform(post("/addAddress").with(jwt().authorities(customerAuthority))
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(address)))
				.andExpect(status().is(200)).andDo(print())
				.equals(address);
	}

	@Test
	void getAllAddresses() throws Exception {
		List<Address> list = new ArrayList<Address>();
		list.add(new Address());
		list.add(new Address());

		when(addressService.getAllAddress()).thenReturn(list);
		
		mockMvc.perform(get("/listOfAddress").with(jwt().authorities(adminAuthority)).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(2)));
	}

	@Test
	@WithMockUser(username = "tuco", authorities = "Customer")
	void getAddressbyId() throws Exception {
		
		Address address = new Address();
		address.setAddressId(123);
		List<Address> list = new ArrayList<Address>();
		list.add(address);
		when(addressService.getAddressByAccount("tuco")).thenReturn(list);
		
		mockMvc.perform(get("/myAddress").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].addressId", is(123)));
	}

	@Test
	@WithMockUser(username = "tuco", authorities = "Customer")
	void updateAddress() throws JsonProcessingException, Exception {
		Address address = new Address();
		address.setAddressId(123);
		
		Optional<Address> aOp = Optional.of(address);
		// when(addressService.updateAddress(address)).thenReturn(address);
		when(addressRepo.findById(123)).thenReturn(aOp);
		
		mockMvc.perform(put("/updateAddress/{id}", 123)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(address))).andDo(print())
				.andExpect(status().is(200));
	}

	@Test
	void deleteAddress() throws Exception {
		mockMvc.perform(delete("/deleteAddress/{id}", 123).with(jwt().authorities(customerAuthority))).andDo(print())
				.andExpect(status().isOk());
	}
}