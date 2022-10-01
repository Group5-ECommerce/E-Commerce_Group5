package com.hcl.controller;

import static org.mockito.Mockito.inOrder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.entity.Address;
import com.hcl.entity.Order;
import com.hcl.entity.PaymentInfo;
import com.hcl.repo.AddressRepository;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.PaymentRepository;
//import com.hcl.entity.User;
//import com.hcl.repo.UserRepository;
import com.hcl.service.AddressService;
//import com.hcl.service.UserService;

import io.swagger.annotations.Api;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags= "Address")
public class AddressController 
{
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@PostMapping("/addAddress")
	public void addAddress(@RequestBody Address a , String okta, Principal principal)
	{
		okta = principal.getName();
		addressService.addAddress(okta, a);		
	}
	
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
	

	
	@PutMapping("/updateAddress/{id}")
	public void updateAddress(@PathVariable Integer id, @RequestBody Address b, Principal principal)
	{
		Address a = addressRepo.findById(id).get();
		a.setOktaId(principal.getName());
		a.setFirstName(b.getFirstName());
		a.setLastName(b.getLastName());
		a.setStreetAddress(b.getStreetAddress());
		a.setCity(b.getCity());
		a.setState(b.getState());
		a.setZip(b.getZip());
		a.setCountry(b.getCountry());
		addressService.updateAddress(a);
	}
	
	@DeleteMapping("/deleteAddress/{id}")
	public void deleteAddress(@PathVariable Integer id)
	{
		Address a = addressRepo.findById(id).get();
		addressRepo.delete(a);
	}
	

}
