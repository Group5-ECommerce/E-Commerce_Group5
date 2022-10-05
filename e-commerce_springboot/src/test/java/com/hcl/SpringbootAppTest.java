package com.hcl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hcl.repo.AddressRepository;
import com.hcl.repo.CartRepository;
import com.hcl.repo.OrderAddressRepository;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.PaymentRepository;
import com.hcl.repo.ProductRatingRepository;

import com.hcl.repo.StorageRepository;
import com.hcl.service.AddressService;
import com.hcl.service.OrderService;
import com.hcl.service.SendEmail;



@SpringBootTest
public class SpringbootAppTest {
	
		@Autowired
		private AddressRepository addressRepository;
		
		@Autowired
		private CartRepository cartRepository;
		
		@Autowired
		private OrderAddressRepository orderaddressRepository;
		
		@Autowired
		private OrderRepository orderRepository;
		
		@Autowired
		private PaymentRepository paymentRepository;
		
		@Autowired
		private ProductRatingRepository productratingRepository;
		
		@Autowired
		private StorageRepository storageRepository;
		
		
		@Autowired
		private AddressService addressService;
				
		@Autowired
		private OrderService orderService;
				
		// @Autowired
		// private SendEmail sendEmail;
		
		@Test
	    public void testAutowired() {
	    	assertNotNull(addressRepository);
	    	assertNotNull(cartRepository);
	    	assertNotNull(orderaddressRepository);
	    	
	    	assertNotNull(orderRepository);
	    	assertNotNull(paymentRepository);
	    	assertNotNull(productratingRepository);
	    	assertNotNull(storageRepository);
	    	assertNotNull(addressService);
	    	assertNotNull(orderService);
	    	
	    	String[] args = {"one", "two"};
	    	SpringBootEx1Application.main(args);
		}

}
