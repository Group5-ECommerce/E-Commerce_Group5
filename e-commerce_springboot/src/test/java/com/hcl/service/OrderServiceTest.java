//package com.hcl.service;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.hcl.entity.Address;
//import com.hcl.entity.Order;

//import com.hcl.entity.OrderItem;
//import com.hcl.entity.Product;
//

//UNFINISHED

//@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//public class OrderServiceTest {
//
//	private OrderService orderService;
//	private Order order;
//	private Address address;
//	private List<OrderItem> items;
//
//	@BeforeEach
//	public void init() {
//		orderService = new OrderService();
//		order = new Order();
//		address = new Address();
//		order.setOktaId("okta");
//		order.setOrderStatus("SHIPPED");
//		order.setShippingAddress(address);
//		order.setOrderTime(new Timestamp(0));
//
//		items = new ArrayList();
//		for (int i = 0; i < 3; i++) {
//			OrderItem orderItem = new OrderItem();
//
//			Product product = new Product();
//			product.setProductPrice(i + 5);
//
//			orderItem.setAmt(new Random().ints(1, 10).limit(1).findFirst().getAsInt());
//			orderItem.setOrder(order);
//			orderItem.setProduct(product);
//			items.add(orderItem);
//		}
//		order.setTotalPrice(0);
//		order.setItems(items);
//		double total = items.stream().map((item) -> {
//			return item.getAmt() * item.getProduct().getProductPrice();
//		}).reduce(Double::sum).get();
//		order.setTotalPrice(total);
//
//		orderService.save(order);
//
//	}
//
//	@AfterEach
//	public void fin() {
//		orderService.deleteAll();
//	}
//
//	@Test
//	void findAll() {
//
//	}
//
//	@Test
//	void findById() {
//
//	}
//
//	@Test
//	void findByOktaId() {
//
//	}
//
//	@Test
//	void findByTrackingNumber() {
//
//	}
//
//	@Test
//	void deleteById() {
//
//	}
//}
