package com.hcl.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.entity.Address;
import com.hcl.entity.OrderAddress;
import com.hcl.repo.AddressRepository;
import com.hcl.repo.OrderAddressRepository;

@ExtendWith(MockitoExtension.class)
public class MockAddressServiceTest {

	@InjectMocks
	AddressService addressService;

	@Mock
	AddressRepository addressRepository;
	
	@Mock
	OrderAddressRepository orderAddressRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);

	}

	// add address
	@Test
	void addAddress() {
		Address address = new Address();
		address.setOktaId("okta1");
		when(addressRepository.save(address)).thenReturn(address);

		Address newAddress = addressService.addAddress("okta1", address);
		Assertions.assertThat(newAddress).extracting(Address::getOktaId).isEqualTo(address.getOktaId());
		verify(addressRepository, times(1)).save(address);
		
		OrderAddress oa = new OrderAddress();
		when(orderAddressRepository.save(oa)).thenReturn(oa);

		OrderAddress response = addressService.addOrderAddress("123", oa);
		Assertions.assertThat(oa.equals(response));
	}

	// get all addresses
	@Test
	void getAllAddress() {
		List<Address> list = new ArrayList();
		list.add(new Address());
		list.add(new Address());
		list.add(new Address());
		when(addressRepository.findAll()).thenReturn(list);
		list = addressService.getAllAddress();
		
		Assertions.assertThat(list).hasSize(3);
		verify(addressRepository, times(1)).findAll();
	}
	
	@Test
	void getAddressByIdAndUpdate() {
		List<Address> list = new ArrayList<Address>();
		Address a = new Address();
		a.setState("NY");
		a.setAddressId(2);
		list.add(a);
		list.add(new Address());
		list.add(new Address());
		
		when(addressRepository.findByOktaId("123")).thenReturn(list);
		list = addressService.getAddressById("123");
		Assertions.assertThat(list).hasSize(3);
		// verify(addressRepository, times(1)).findAll();
		
		a.setState("PA");
		addressService.updateAddress(a);
	}
}
