package com.hcl.repo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.entity.Product;

//Calls
@ExtendWith(MockitoExtension.class)
public class MockProductRepositoryTest {

	@Mock
	ProductRepository mockProductRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void testFindAllProducts() {
		List<Product> list = new ArrayList();
		for (int i = 0; i < 8; i++) {
			Product product = new Product();
			product.setProductId(i);
			product.setProductName(RandomStringUtils.randomAlphanumeric(10));
			product.setProductImage(product.getProductName());
			product.setProductPrice(new Random().doubles(5, 1000).limit(1).findFirst().getAsDouble());
			product.setStorageId("Washington");
//			mockProductRepository.save(product);
			list.add(product);
		}
		when(mockProductRepository.findAll()).thenReturn(list);
		list = mockProductRepository.findAll();
		Assertions.assertThat(list).hasSize(8);
		verify(mockProductRepository, times(1)).findAll();

	}

	@Test
	void testFindProductById() {
		Product found = new Product();
		mockProductRepository.save(found);
		verify(mockProductRepository, times(1)).save(found);
		mockProductRepository.findById(found.getProductId());
		verify(mockProductRepository, times(1)).findById(found.getProductId());
	}

	@Test
	void testCreateUpdateProduct() {
		Product product = new Product();
		product.setProductId(1);
		product.setProductName(RandomStringUtils.randomAlphanumeric(10));
		product.setProductImage(product.getProductName());
		product.setProductPrice(new Random().doubles(5, 1000).limit(1).findFirst().getAsDouble());
		product.setStorageId("Washington");

		mockProductRepository.save(product);
		verify(mockProductRepository, times(1)).save(product);
	}

	@Test
	public void deleteProduct() {
		Product deleted = new Product();
		mockProductRepository.save(deleted);
		verify(mockProductRepository, times(1)).save(deleted);
		mockProductRepository.deleteById(deleted.getProductId());
		verify(mockProductRepository, times(1)).deleteById(deleted.getProductId());
	}

}