package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.entity.PaymentInfo;

@Repository

public interface PaymentRepository extends JpaRepository<PaymentInfo,Integer>{

}
