package com.hcl.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.entity.Product;
import com.hcl.repo.ProductRepository;

//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.ANY)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ProductRepositoryTest {

	@Autowired
	ProductRepository productRepository;

	@BeforeEach
	public void init() {
		assertNotNull(productRepository);
	}

	@AfterEach
	public void fin() {
		productRepository.deleteAll();
	}

	@Test
	void saveFindAll() {
		Product product = new Product();
		product.setProductName(RandomStringUtils.randomAlphanumeric(10));
		product.setProductImage(product.getProductName());
		product.setProductPrice(new Random().doubles(5, 1000).limit(1).findFirst().getAsDouble());
		product.setStorageId("Washington");
		product.setCategory("Device");
		product.setProductStock(22);
		// embedded db
		productRepository.save(product);
		
		List<Product> products = productRepository.findAll();
		Assertions.assertThat(products).extracting(Product::getProductId).containsOnly(product.getProductId());
	}

	@Test
	void saveFindById() {
		Product product = new Product();
		productRepository.save(product);
		Product returned = productRepository.findById(product.getProductId()).get();
		Assertions.assertThat(returned.equals(product));
		Assertions.assertThat(returned).extracting(Product::getProductId).isEqualTo(returned.getProductId());
	}

	@Test
	void saveDeleteById() {
		Product product = new Product();
		productRepository.save(product);
		productRepository.deleteById(product.getProductId());
		Assertions.assertThat(productRepository.findAll()).isEmpty();
	}
	
	@Test
	void allArgsConstructorAndMethods() {
		Product product2 = new Product(5, "name", 5, "testImg", 1.23, "1", "Device");
		product2.hashCode();
		product2.toString();
	}

}