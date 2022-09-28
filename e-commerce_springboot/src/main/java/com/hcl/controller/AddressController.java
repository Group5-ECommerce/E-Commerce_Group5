package com.hcl.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.entity.Address;
//import com.hcl.entity.User;
//import com.hcl.repo.UserRepository;
import com.hcl.service.AddressService;
//import com.hcl.service.UserService;

import io.swagger.annotations.Api;


@RestController
@Api(tags= "Address")
public class AddressController 
{
	@Autowired
	private AddressService addressService;
	
	
	@GetMapping("/listOfAddress")
	public List<Address> getAddress()
	{
		List<Address> list = addressService.getAllAddress();
		return list;
	}
	
	@GetMapping("/listOfAddressById")
	public List<Address> getAddressById(Principal principal)
	{
		List<Address> list = addressService.getAddressById(principal.getName());
		return list;
		
	}

}
