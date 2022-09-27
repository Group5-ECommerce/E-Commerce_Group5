package com.hcl.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.hcl.entity.Order;

public class OrderControllerTests {
	
	@Test
	void checkout() {
		OrderController oc = new OrderController();
		
		// oc.checkout(null, null, null, null);
		// when(orderRepository.findAll()).thenReturn(list);
		// list = orderService.findAll();
		// Assertions.assertThat(list).hasSize(2);
		// verify(orderRepository, times(1)).findAll();
	}
}
