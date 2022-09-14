package com.hcl.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.entity.Cart;
import com.hcl.other.CartId;

public interface CartRepository extends JpaRepository<Cart, CartId> {
	List<Cart> findByOktaId(String oktaId);

	Integer deleteAllByOktaId(String oktaId);
}
