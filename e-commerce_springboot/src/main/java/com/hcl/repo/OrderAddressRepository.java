package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.entity.OrderAddress;

@Repository
public interface OrderAddressRepository extends JpaRepository<OrderAddress,Integer>
{

}
