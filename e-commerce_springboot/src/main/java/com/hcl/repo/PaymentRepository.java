package com.hcl.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.entity.Address;
import com.hcl.entity.Order;
import com.hcl.entity.PaymentInfo;

@Repository

public interface PaymentRepository extends JpaRepository<PaymentInfo,Integer>{

	List<PaymentInfo> findByShippingAddressId(Address shippingAddressId);
	List<PaymentInfo> findByBillingAddressId(Address billingAddressId);

}
