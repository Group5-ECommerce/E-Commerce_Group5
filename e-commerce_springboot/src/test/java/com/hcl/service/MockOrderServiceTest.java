package com.hcl.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.entity.Order;
import com.hcl.repo.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class MockOrderServiceTest {

	@InjectMocks
	OrderService orderService;

	@Mock
	OrderRepository orderRepository;

	private Order order;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		order = new Order();
		order.setTrackingNumber("tracking1");
		orderService.save(order);
		verify(orderRepository, times(1)).save(order);
	}

	@Test
	void findAll() {

		List<Order> list = new ArrayList();
		list.add(new Order());
		list.add(new Order());
		when(orderRepository.findAll()).thenReturn(list);
		list = orderService.findAll();
		Assertions.assertThat(list).hasSize(2);
		verify(orderRepository, times(1)).findAll();

	}

	@Test
	void findById() {

		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		Order returnedOrder = orderService.findById(order.getOrderId()).get();
		// assert id
		Assertions.assertThat(returnedOrder).extracting(Order::getOrderId).isEqualTo(order.getOrderId());
		verify(orderRepository, times(1)).findById(order.getOrderId());
	}

	@Test
	void findByOktaId() {

		List<Order> list = new ArrayList();
		Order order1 = new Order();
		order1.setOktaId("okta1");
		Order order2 = new Order();
		order2.setOktaId("okta1");
		Order order3 = new Order();
		order3.setOktaId("okta3");
		list.add(order1);
		list.add(order2);
		list.add(order3);

		when(orderRepository.findByOktaId("okta1"))
				.thenReturn(list.stream().filter(order -> order.getOktaId() == "okta1").collect(Collectors.toList()));
		List<Order> returnedList = orderService.findByOktaId("okta1");
		// assert id
		Assertions.assertThat(returnedList).extracting(Order::getOktaId).containsOnly("okta1");
		verify(orderRepository, times(1)).findByOktaId("okta1");
	}

	@Test
	void findByOrderTrackingNumber() {
		when(orderRepository.findByTrackingNumber(order.getTrackingNumber())).thenReturn(Optional.of(order));
		Order returnedOrder = orderService.findByTrackingNumber(order.getTrackingNumber()).get();
		// assert id
		Assertions.assertThat(returnedOrder).extracting(Order::getTrackingNumber).isEqualTo(order.getTrackingNumber());
		verify(orderRepository, times(1)).findByTrackingNumber(order.getTrackingNumber());
	}

	@Test
	void deleteById() {

		orderService.deleteById(order.getOrderId());
		verify(orderRepository, times(1)).deleteById(order.getOrderId());

		Assertions.assertThat(orderService.findAll()).isEmpty();
	}

	@Test
	void deleteAll() {

		orderService.save(new Order());
		orderService.save(new Order());
		orderService.deleteAll();
		verify(orderRepository, times(1)).deleteAll();
		Assertions.assertThat(orderService.findAll()).isEmpty();
	}
}
