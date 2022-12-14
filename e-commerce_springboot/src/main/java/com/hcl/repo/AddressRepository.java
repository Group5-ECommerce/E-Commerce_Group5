package com.hcl.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.entity.Address;
//import com.hcl.entity.User;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer>{
	

	//Optional<Address> findByUserId(Integer userId);
	
	List <Address> findByOktaId(String oktaId);
	//List<Address> findByStreetAddress(String streetAddress);

//	boolean exists(Address b);

}
