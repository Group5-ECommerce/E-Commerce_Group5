package com.hcl.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.entity.Address;
import com.hcl.entity.User;
import com.hcl.repo.UserRepository;
import com.hcl.service.AddressService;
import com.hcl.service.UserService;


@RestController
public class AddressController 
{
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService service;
	
	@PostMapping("/addAddress")
	public String addAddress(Principal principal, @RequestBody Address a)
	{
	
		Optional<User>user = service.getUserByUsername(principal.getName());
		User u = user.get();
		addressService.addAddress(u, a);
		return "Address added successfully!";
	}
	
	@GetMapping("/listOfAddress")
	public List<Address> getAddress()
	{
		List<Address> list = addressService.getAllAddress();
		return list;
	}

}
