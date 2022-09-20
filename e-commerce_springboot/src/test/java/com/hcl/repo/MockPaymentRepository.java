package com.hcl.repo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.entity.PaymentInfo;

@ExtendWith(MockitoExtension.class)
public class MockPaymentRepository {

	@Mock
	PaymentRepository mockPaymentRepository;

	PaymentInfo paymentInfo;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		paymentInfo = new PaymentInfo();
	}

	// save
	@Test
	void findById() {
		mockPaymentRepository.save(paymentInfo);
		verify(mockPaymentRepository, times(1)).save(paymentInfo);

		mockPaymentRepository.findById(paymentInfo.getPaymentId());
		verify(mockPaymentRepository, times(1)).findById(paymentInfo.getPaymentId());
	}

	// get all
	@Test
	void findAll() {
		List<PaymentInfo> list = new ArrayList();
		list.add(new PaymentInfo());
		list.add(new PaymentInfo());
		list.add(new PaymentInfo());
		when(mockPaymentRepository.findAll()).thenReturn(list);
		list = mockPaymentRepository.findAll();
		Assertions.assertThat(list).hasSize(3);
		verify(mockPaymentRepository, times(1)).findAll();
	}

	// delete by id
	@Test
	void deleteById() {
		mockPaymentRepository.save(paymentInfo);
		verify(mockPaymentRepository, times(1)).save(paymentInfo);
		mockPaymentRepository.deleteById(paymentInfo.getPaymentId());
		verify(mockPaymentRepository, times(1)).deleteById(paymentInfo.getPaymentId());
	}
}
