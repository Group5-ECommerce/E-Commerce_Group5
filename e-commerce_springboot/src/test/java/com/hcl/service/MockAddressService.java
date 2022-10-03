package com.hcl.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.entity.Address;
import com.hcl.repo.AddressRepository;

@ExtendWith(MockitoExtension.class)
public class MockAddressService {

	@InjectMocks
	AddressService addressService;

	@Mock
	AddressRepository addressRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);

	}

	// add address
	void addAddress() {
		Address address = new Address();
		address.setOktaId("okta1");
		when(addressRepository.save(address)).thenReturn(address);

		Address newAddress = addressService.addAddress("okta1", address);
		Assertions.assertThat(newAddress).extracting(Address::getOktaId).isEqualTo(address.getOktaId());
		verify(addressRepository, times(1)).save(address);
	}

	// get all addresses

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
}
