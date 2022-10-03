package com.hcl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.entity.Address;
import com.hcl.entity.OrderAddress;
//import com.hcl.entity.User;
import com.hcl.repo.AddressRepository;
import com.hcl.repo.OrderAddressRepository;
//import com.hcl.repo.UserRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repo;
	
	@Autowired
	private OrderAddressRepository orderAddressRepo;

	public Address addAddress(String oktaId, Address a) {
		a.setOktaId(oktaId);
		return repo.save(a);
	}

	public List<Address> getAllAddress() {
		return repo.findAll();
	}
	
	public List<Address> getAddressById(String oktaId)
	{
		List <Address> address = repo.findByOktaId(oktaId);
		return address;
		
	}
	
	public Address updateAddress(Address a)
	{
		return repo.save(a);
	}
	
	public OrderAddress addOrderAddress(String oktaId, OrderAddress a) {
		a.setOktaId(oktaId);
		return orderAddressRepo.save(a);
	}

}
