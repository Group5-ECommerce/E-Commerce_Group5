package com.hcl.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.entity.Address;
//import com.hcl.entity.User;
import com.hcl.repo.AddressRepository;
//import com.hcl.repo.UserRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repo;
	
	
	
	public void addAddress(String oktaId , Address a) 
	{
		a.setOktaId(oktaId);
		repo.save(a);
	}

	public List<Address> getAllAddress() {
		return repo.findAll();
	}

}
