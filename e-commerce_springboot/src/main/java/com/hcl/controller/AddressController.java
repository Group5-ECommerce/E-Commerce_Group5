package com.hcl.controller;

import static org.mockito.Mockito.inOrder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.AddressDTO;
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
	@PreAuthorize("hasAuthority('Customer') and !hasAuthority('Admin')")
	public void addAddress(@RequestBody AddressDTO a_dto, Principal principal)
	{
		String okta = principal.getName();
		
		Address a = new Address();
		a.setAddressId(a_dto.getAddressId());
		a.setCity(a_dto.getCity());
		a.setCountry(a_dto.getCountry());
		a.setFirstName(a_dto.getFirstName());
		a.setLastName(a_dto.getLastName());
		a.setState(a_dto.getState());
		a.setStreetAddress(a_dto.getStreetAddress());
		a.setZip(a_dto.getZip());
		
		addressService.addAddress(okta, a);
	}
	
	@GetMapping("/listOfAddress")
	@PreAuthorize("hasAuthority('Admin')")
	public List<Address> getAddress()
	{
		List<Address> list = addressService.getAllAddress();
		return list;
	}
	
	@GetMapping("/myAddress")
	@PreAuthorize("hasAuthority('Customer')")
	public List<Address> getMyOrders(Principal principal)
	{
		List<Address> list = addressService.getAddressByAccount(principal.getName());
		return list;
	}
	

	
	@PutMapping("/updateAddress/{id}")
	@PreAuthorize("hasAuthority('Customer') or hasAuthority('Admin')")
	public void updateAddress(@PathVariable Integer id, @RequestBody AddressDTO b, Principal principal)
	{
		Optional<Address> a_op = addressRepo.findById(id);
		
		if (!a_op.isPresent()) return;
		
		Address a = a_op.get();
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
	@PreAuthorize("hasAuthority('Customer') or hasAuthority('Admin')")
	public void deleteAddress(@PathVariable Integer id)
	{
		Optional<Address> a_op = addressRepo.findById(id);
		
		if (a_op.isPresent()) {
			addressRepo.delete(a_op.get());
		}
	}
	

}
